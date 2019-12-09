package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> {
  val busyDrivers = this.trips.map { it.driver }.distinct().toSet()
  return this.allDrivers.minus(busyDrivers);
}

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
  return when (minTrips) {
    0 -> this.allPassengers
    else -> this.trips.flatMap { it.passengers }
      .groupBy { it }
      .filterValues { minTrips <= it.count() }.keys
  }
}

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
  return this.trips.filter { it.driver == driver }
    .flatMap { it.passengers }
    .groupBy { it }
    .filterValues { 1 < it.count() }.keys
}

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
  return trips.flatMap { t ->
    t.passengers.map { it to t.discount }
  }.groupBy(keySelector = { it.first }, valueTransform = { it.second })
    .filter { (it.value.filterNotNull().count() * 2) > it.value.count() }.keys
}

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
  val frequentPeriod = this.trips.map { it.duration }
    .groupBy { (it / 10) * 10 }
    .mapValues { it.value.count() }
    .maxBy { it.value }
  return frequentPeriod?.let { IntRange(it.key, it.key + 9) }
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
  return when {
    this.trips.isEmpty() -> false
    else -> {
      val sorted = this.trips.map { it.driver to it.cost }
        .groupBy(keySelector = { it.first }, valueTransform = { it.second })
        .mapValues { it.value.sum() }
        .values.sortedDescending()
      val toIndex = (this.allDrivers.count() * 0.2).toInt()
      val adjustedToIndex = if (toIndex < sorted.size) toIndex else sorted.size
      val coreDriverTotal = sorted.subList(0, adjustedToIndex).sum()
      val total = sorted.sum()
      return (coreDriverTotal / total) >= 0.8
    }
  }
}