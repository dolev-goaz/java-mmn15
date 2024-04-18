package q2;

import java.util.Random;

public class Main {
    private static final int FIRST_RUNWAY_COUNT = 3;
    private static final int SECOND_RUNWAY_COUNT = 3;
    private static final int FLIGHT_COUNT = 10;

    public static void main(String[] args) {
        Airport first = new Airport("Israel", FIRST_RUNWAY_COUNT);
        Airport second = new Airport("Greece", SECOND_RUNWAY_COUNT);
        Flight[] flights = generateFlights(first, second);
        runFlights(flights);
    }

    private static void runFlights(Flight[] flights) {
        for (Flight flight : flights) {
            flight.start();
        }
        try {
            for (Flight flight : flights) {
                flight.join();
            }
        } catch (InterruptedException e) {
        }
    }

    private static Flight[] generateFlights(Airport first, Airport second) {
        Flight[] flights = new Flight[FLIGHT_COUNT];
        for (int i = 0; i < FLIGHT_COUNT; i++) {
            flights[i] = createRandomFlight(500 + i, first, second);
        }
        return flights;
    }

    private static Flight createRandomFlight(int flightNo, Airport first, Airport second) {
        boolean isDepartFromFirst = new Random().nextBoolean();

        Airport source, target;
        if (isDepartFromFirst) {
            source = first;
            target = second;
        } else {
            source = second;
            target = first;
        }

        return new Flight(flightNo, source, target);
    }
}
