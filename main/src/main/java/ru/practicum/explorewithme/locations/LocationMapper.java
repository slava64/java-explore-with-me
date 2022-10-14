package ru.practicum.explorewithme.locations;

public class LocationMapper {
    public static Location toLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setLon(locationDto.getLon());
        location.setLat(locationDto.getLat());

        return location;
    }

    public static LocationDto toLocationDto(Location location) {
        return new LocationDto(
                location.getLon(),
                location.getLat()
        );
    }

}
