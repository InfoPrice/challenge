package com.infoprice.infopricechallenge.populators;

import com.infoprice.infopricechallenge.dto.AddressDTO;
import com.infoprice.infopricechallenge.entities.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public interface AddressReversePopulator {

    Address populate(AddressDTO source, Address target);

}
