package be.vn.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import be.vn.constant.CommonConstant;
import be.vn.model.AddressCoin;
import be.vn.model.AddressGmail;
import be.vn.model.Gmail;
import be.vn.repository.AddressCoinRepository;
import be.vn.repository.AddressGmailRepository;
import be.vn.repository.GmailRepository;

import java.util.Random;

/**
 * YoiTVLogic class
 *
 * @author TuanDV <tuandv@hospital.vn>
 */
@Service
public class PontemLogic {
    @Autowired
    private GmailRepository gmailRepository;
    @Autowired
    private AddressCoinRepository addressCoinRepository;
    @Autowired
    private AddressGmailRepository addressGmailRepository;

    public Gmail getGmail() {
        Gmail gmail = gmailRepository.findFirstByStatus(1);
        gmail.setStatus(3);
        gmailRepository.save(gmail);
        return gmail;
    }

    public AddressCoin getAddress() {
        AddressCoin addressCoin = addressCoinRepository.findFirstByStatus(1);
        addressCoin.setStatus(3);
        addressCoinRepository.save(addressCoin);
        return addressCoin;
    }

    public String update(String email, Integer address) {
        Gmail gmail = gmailRepository.findFirstByUserName(email);
        gmail.setStatus(2);
        gmailRepository.save(gmail);

        AddressCoin addressCoin = addressCoinRepository.findFirstById(address);
        addressCoin.setStatus(2);
        addressCoinRepository.save(addressCoin);

        AddressGmail addressGmail = new AddressGmail();
        addressGmail.setGmailId(gmail.getId());
        addressGmail.setAddressId(addressCoin.getId());
        addressGmailRepository.save(addressGmail);
        return "OK";
    }
}