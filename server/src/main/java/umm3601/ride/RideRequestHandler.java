package umm3601.ride;

import org.bson.Document;
import spark.Request;
import spark.Response;

public class RideRequestHandler {

  private final RideController rideController;

  public RideRequestHandler(RideController rideController) {
    this.rideController = rideController;
  }

  /**
   * Method called from Server when the 'api/rides/:id' endpoint is received.
   * Get a JSON response with a list of all the rides in the database.
   *
   * @param req the HTTP request
   * @param res the HTTP response
   * @return one ride in JSON formatted string and if it fails it will return text with a different HTTP status code
   */
  public String getRideJSON(Request req, Response res) {
    res.type("application/json");
    String id = req.params("id");
    String ride;
    try {
      ride = rideController.getRide(id);
    } catch (IllegalArgumentException e) {
      // This is thrown if the ID doesn't have the appropriate
      // form for a Mongo Object ID.
      // https://docs.mongodb.com/manual/reference/method/ObjectId/
      res.status(400);
      res.body("The requested ride id " + id + " wasn't a legal Mongo Object ID.\n" +
        "See 'https://docs.mongodb.com/manual/reference/method/ObjectId/' for more info.");
      return "";
    }
    if (ride != null) {
      return ride;
    } else {
      res.status(404);
      res.body("The requested ride with id " + id + " was not found");
      return "";
    }
  }


  /**
   * Method called from Server when the 'api/rides' endpoint is received.
   * This handles the request received and the response that will be sent back.
   *
   * @param req the HTTP request
   * @param res the HTTP response
   * @return an array of rides in JSON formatted String
   */
  public String getRides(Request req, Response res) {
    res.type("application/json");
    return rideController.getRides(req.queryMap().toMap());
  }

  /**
   * Method called from Server when the 'api/rides/new' endpoint is received.
   * Gets specified rides info from request and calls addNewRide helper method
   * to append that info to a document
   *
   * @param req the HTTP request
   * @param res the HTTP response
   * @return a boolean as whether the ride was added successfully or not
   */
  public String addNewRide(Request req, Response res) {
    res.type("application/json");

    Document newRide = Document.parse(req.body());

    String driver = newRide.getString("driver");
    String notes = newRide.getString("notes");
    int seatsAvailable = newRide.getInteger("seatsAvailable");
    String origin = newRide.getString("origin");
    String destination = newRide.getString("destination");
    String departureTime = newRide.getString("departureTime");
    //Date from the datepicker is by default in ISO time, like "2019-03-13T05:00:00.000Z". departureDateISO retrieves that.
    //departureDateYYYYMMDD breaks off the irrelevant end data from the "T" and on. From there, month and day are broken off.
    String departureDateISO = newRide.getString("departureDate");
    String departureDateYYYYMMDD = departureDateISO.split("T",2)[0];
    String departureDateMonth = departureDateYYYYMMDD.split("-",3)[1];
    String departureDateDayUnformatted = departureDateYYYYMMDD.split("-", 3)[2];
    System.out.println("date BEFORE conversion: " + departureDateYYYYMMDD);
    System.out.println("month BEFORE conversion: " + departureDateMonth);
    System.out.println("day BEFORE conversion: " + departureDateDayUnformatted);

//    int departureDateDayInt = Integer.parseInt(departureDateDayUnformatted);
//    if(departureDateDayInt == 1 || departureDateDayInt == 21 || departureDateDayInt == 31) {
//      String departureDateDay = departureDateDayUnformatted.concat("st");
//    } else if (departureDateDayInt == 2 || departureDateDayInt == 22) {
//      String departureDateDay = departureDateDayUnformatted.concat("nd");
//    } else if (departureDateDayInt == 3 || departureDateDayInt == 23) {
//      String departureDateDay = departureDateDayUnformatted.concat("rd");
//    } else {
//      String departureDateDay = departureDateDayUnformatted.concat("th");
//    }
//
//    String departureDateFinal = ;
//
//    System.out.println("date AFTER conversion: " + departureDateYYYYMMDD);
//    System.out.println("month AFTER conversion: " + departureDateMonth);
//    System.out.println("day AFTER conversion: " + departureDateDay);



    System.err.println("Adding new ride [driver=" + driver + ", notes=" + notes + ", seatsAvailable=" + seatsAvailable
      + ", origin=" + origin + ", destination=" + destination + ", departureTime=" + departureTime + ", departureDate="
      + departureDateYYYYMMDD + ']'); //TODO: change date field name
    return rideController.addNewRide(driver, notes, seatsAvailable, origin, destination, departureTime, departureDateYYYYMMDD); //TODO: change date field name
  }
}
