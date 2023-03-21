package com.hoopoe;

import com.hoopoe.domain.Role;
import com.hoopoe.domain.User;
import com.hoopoe.domain.enums.RoleType;
import com.hoopoe.repository.RoleRepository;
import com.hoopoe.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class HoopoeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoopoeApplication.class, args);
	}

}

	@Component
	@AllArgsConstructor
	class DemoCommandLineRunner implements CommandLineRunner{

	RoleRepository roleRepository;

	PasswordEncoder encoder;

	UserRepository userRepository;
	@Override
	public void run(String... args) throws Exception {

		if(!roleRepository.findByType(RoleType.ROLE_CUSTOMER).isPresent()){
			Role roleCustomer = new Role();

			roleCustomer.setType(RoleType.ROLE_CUSTOMER);
			roleRepository.save(roleCustomer);
		}

		if(!roleRepository.findByType(RoleType.ROLE_ADMIN).isPresent()){
			Role roleAdmin = new Role();
			roleAdmin.setType(RoleType.ROLE_ADMIN);
			roleRepository.save(roleAdmin);
		}

		if(!userRepository.findByEmail("superadmin@gmail.com").isPresent()){
			User admin = new User();
			Role role = roleRepository.findByType(RoleType.ROLE_ADMIN).get();
			admin.setRoles(new HashSet<>(Collections.singletonList(role)));
			admin.setEmail("superadmin@gmail.com");
			admin.setFirstName("superadminfirstname");
			admin.setLastName("superadminlastname");
			admin.setPassword(encoder.encode("Mfe666mfe."));
			admin.setPhoneNumber("(552) 069-6293");
			userRepository.save(admin);
		}

		if(!userRepository.findByEmail("supercustomer@gmail.com").isPresent()){
			User customer = new User();
			Role role = roleRepository.findByType(RoleType.ROLE_CUSTOMER).get();
			customer.setRoles(new HashSet<>(Collections.singletonList(role)));
			customer.setEmail("supercustomer@gmail.com");
			customer.setFirstName("supercustomername");
			customer.setLastName("supercustomerlastname");
			customer.setPassword(encoder.encode("Mfe666mfe."));
			customer.setPhoneNumber("(552) 069-6293");
			userRepository.save(customer);


		}
	}
}
