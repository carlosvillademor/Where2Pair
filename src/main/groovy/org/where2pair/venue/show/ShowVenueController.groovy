package org.where2pair.venue.show

import org.where2pair.venue.Venue
import org.where2pair.venue.VenueJsonMarshaller
import org.where2pair.venue.VenueRepository
import org.where2pair.venue.find.ErrorResponse

class ShowVenueController {
    VenueRepository venueRepository
    VenueJsonMarshaller venueJsonMarshaller

    def show(long id) {
        Venue venue = venueRepository.get(id)

        if (!venue) {
            return new ErrorResponse(message: "Venue with id $id could not be found", status: 404)
        }
        return venueJsonMarshaller.asVenueJson(venue)
    }

    def showAll() {
        List venues = venueRepository.getAll()
        return venueJsonMarshaller.asVenuesJson(venues)
    }

}
