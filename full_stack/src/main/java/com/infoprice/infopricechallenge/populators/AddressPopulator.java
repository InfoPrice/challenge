package com.infoprice.infopricechallenge.populators;

import com.infoprice.infopricechallenge.dto.AddressDTO;
import com.infoprice.infopricechallenge.entities.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public interface AddressPopulator {

    AddressDTO populate(final Address source, final AddressDTO target);

}
