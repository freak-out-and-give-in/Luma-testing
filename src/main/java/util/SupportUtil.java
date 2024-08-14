package util;

import support.AdvancedSearch;
import support.Checkout;

import java.util.Optional;

public class SupportUtil {

    public static void changeAdvancedSearchNullValuesToEmpty(AdvancedSearch advancedSearch) {
        advancedSearch.setProductName(Optional.ofNullable(advancedSearch.getProductName()).orElse(""));
        advancedSearch.setSku(Optional.ofNullable(advancedSearch.getSku()).orElse(""));
        advancedSearch.setDescription(Optional.ofNullable(advancedSearch.getDescription()).orElse(""));
        advancedSearch.setShortDescription(Optional.ofNullable(advancedSearch.getShortDescription()).orElse(""));
        advancedSearch.setLowerPrice(Optional.ofNullable(advancedSearch.getLowerPrice()).orElse(""));
        advancedSearch.setUpperPrice(Optional.ofNullable(advancedSearch.getUpperPrice()).orElse(""));
    }

    public static void changeCheckoutNullValuesToEmpty(Checkout checkout) {
        checkout.setEmail(Optional.ofNullable(checkout.getEmail()).orElse(""));
        checkout.setFirstName(Optional.ofNullable(checkout.getFirstName()).orElse(""));
        checkout.setLastName(Optional.ofNullable(checkout.getLastName()).orElse(""));
        checkout.setCompany(Optional.ofNullable(checkout.getCompany()).orElse(""));
        checkout.setStreetAddress1(Optional.ofNullable(checkout.getStreetAddress1()).orElse(""));
        checkout.setStreetAddress2(Optional.ofNullable(checkout.getStreetAddress2()).orElse(""));
        checkout.setStreetAddress3(Optional.ofNullable(checkout.getStreetAddress3()).orElse(""));
        checkout.setCity(Optional.ofNullable(checkout.getCity()).orElse(""));
        checkout.setPostalCode(Optional.ofNullable(checkout.getPostalCode()).orElse(""));
        checkout.setCountry(Optional.ofNullable(checkout.getCountry()).orElse(""));
        checkout.setPhoneNumber(Optional.ofNullable(checkout.getPhoneNumber()).orElse(""));
    }
}
