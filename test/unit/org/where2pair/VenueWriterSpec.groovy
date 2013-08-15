package org.where2pair

import spock.lang.Specification

class VenueWriterSpec extends Specification {

	VenueRepository venueRepository = Mock()
	VenueWriter venueWriter = new VenueWriter(venueRepository: venueRepository)
	
	def "when no matching Venue already exists, then saves new Venue"() {
		given:
		Venue venue = new Venue(name: 'name', location: new Coordinates(1.0, 0.1))
		venueRepository.findByNameAndCoordinates('name', new Coordinates(1.0, 0.1)) >> null
		venueRepository.save(venue) >> 99
		
		when:
		long id = venueWriter.save(venue)
		
		then:
		id == 99
		1 * venueRepository.save(venue)
	}
	
	def "when matching Venue is found, then saves new Venue"() {
		given:
		Venue venue = new Venue(id: 0, name: 'name', location: new Coordinates(1.0, 0.1))
		Venue matchingVenue = new Venue(id: 99)
		venueRepository.findByNameAndCoordinates('name', new Coordinates(1.0, 0.1)) >> matchingVenue
		
		when:
		long id = venueWriter.save(venue)
		
		then:
		id == 99
		1 * venueRepository.update({ it == venue && it.id == 99 })
	}
}
