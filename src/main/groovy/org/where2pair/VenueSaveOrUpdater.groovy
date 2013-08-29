package org.where2pair

class VenueSaveOrUpdater {

	VenueRepository venueRepository
	
	long save(Venue venue) {
		Venue existingVenue = venueRepository.findByNameAndCoordinates(venue.name, venue.location)
		
		if (existingVenue) {
			venue.id = existingVenue.id
			venueRepository.update(venue)
			return venue.id
		} else {
			return venueRepository.save(venue)
		}
	}
	
}