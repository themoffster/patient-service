package uk.com.poodle.service;

import lombok.experimental.UtilityClass;
import uk.com.poodle.data.AddressEntity;
import uk.com.poodle.domain.Address;

import static java.lang.String.join;

@UtilityClass
class AddressMapper {

    public static Address map(AddressEntity entity) {
        return Address.builder()
            .id(entity.getId())
            .number(entity.getNumber())
            .street(entity.getStreet())
            .town(entity.getTown())
            .postcode(buildPostcode(entity))
            .build();
    }

    public static AddressEntity map(Address address) {
        return AddressEntity.builder()
            .id(address.getId())
            .inboundPostcode(getInboundPostcode(address.getPostcode()).toUpperCase())
            .number(address.getNumber())
            .outboundPostcode(getOutboundPostcode(address.getPostcode()).toUpperCase())
            .street(address.getStreet())
            .town(address.getTown())
            .build();
    }

    private static String buildPostcode(AddressEntity address) {
        return join(" ", address.getOutboundPostcode(), address.getInboundPostcode());
    }

    private static String getOutboundPostcode(String postcode) {
        return postcode.substring(0, postcode.length() - 3).trim();
    }

    private static String getInboundPostcode(String postcode) {
        return postcode.substring(postcode.length() - 3).trim();
    }
}
