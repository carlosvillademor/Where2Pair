package org.where2pair.grails

import static org.where2pair.DayOfWeek.MONDAY
import static org.where2pair.DayOfWeek.TUESDAY
import static org.where2pair.DayOfWeek.FRIDAY

import org.where2pair.WeeklyOpeningTimes;
import org.where2pair.WeeklyOpeningTimesBuilder
import org.where2pair.DailyOpeningTimes.SimpleTime

import spock.lang.Specification

class OpenHoursJsonMarshallerSpec extends Specification {

	WeeklyOpeningTimes weeklyOpeningTimes 
	OpenHoursJsonMarshaller openHoursJsonMarshaller = new OpenHoursJsonMarshaller()

	void setup() {
		WeeklyOpeningTimesBuilder builder = new WeeklyOpeningTimesBuilder()
		builder.addOpenPeriod(MONDAY, new SimpleTime(12, 0), new SimpleTime(18, 30))
		builder.addOpenPeriod(TUESDAY, new SimpleTime(8, 0), new SimpleTime(11, 0))
		builder.addOpenPeriod(FRIDAY, new SimpleTime(9, 0), new SimpleTime(12, 0))
		builder.addOpenPeriod(FRIDAY, new SimpleTime(13, 0), new SimpleTime(15, 0))
		weeklyOpeningTimes = builder.build()
	}	
	
	def "converts weekly opening times to json"() {
		when:
		Map openHoursJson = openHoursJsonMarshaller.asOpenHoursJson(weeklyOpeningTimes)

		then:
		openHoursJson == [monday: [
				[openHour: 12, openMinute: 0, closeHour: 18, closeMinute: 30]
			],
			tuesday: [
				[openHour: 8, openMinute: 0, closeHour: 11, closeMinute: 0]
			],
			wednesday: [],
			thursday: [],
			friday: [
				[openHour: 9, openMinute: 0, closeHour: 12, closeMinute: 0],
				[openHour: 13, openMinute: 0, closeHour: 15, closeMinute: 0]
			],
			saturday: [],
			sunday: []] as LinkedHashMap
	}

	def "converts json to weekly opening times"() {
		given:
		Map openHoursJson = [monday: [[openHour: 12, openMinute: 0, closeHour: 18, closeMinute: 30]],
			tuesday: [[openHour: 8, openMinute: 0, closeHour: 11, closeMinute: 0]],
			wednesday: [],
			thursday: [],
			friday: [[openHour: 9, openMinute: 0, closeHour: 12, closeMinute: 0],
				[openHour: 13, openMinute: 0, closeHour: 15, closeMinute: 0]],
			saturday: [],
			sunday: []] as LinkedHashMap
		
		when:
		WeeklyOpeningTimes result = openHoursJsonMarshaller.asWeeklyOpeningTimes(openHoursJson)
		
		then:
		result == weeklyOpeningTimes
	}
	
	def "handles non-integer types for hour/minute values"() {
		given:
		Map openHoursJson = [monday: [[openHour: "12", openMinute: "0", closeHour: "18", closeMinute: "30"]],
			tuesday: [[openHour: "8", openMinute: "0", closeHour: "11", closeMinute: "0"]],
			wednesday: [],
			thursday: [],
			friday: [[openHour: "9", openMinute: "0", closeHour: "12", closeMinute: "0"],
				[openHour: "13", openMinute: "0", closeHour: "15", closeMinute: "0"]],
			saturday: [],
			sunday: []] as LinkedHashMap
		
		when:
		WeeklyOpeningTimes result = openHoursJsonMarshaller.asWeeklyOpeningTimes(openHoursJson)
		
		then:
		result == weeklyOpeningTimes
	}
}