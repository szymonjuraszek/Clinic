package com.szymon.demo.service;

import com.szymon.demo.collections.Doctor;
import com.szymon.demo.collections.Patient;
import com.szymon.demo.repository.DoctorRepository;
import com.szymon.demo.repository.PatientRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;

    public UserDetailsServiceImpl(DoctorRepository doctorRepository,PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(doctorRepository.findByEmail(username) != null){
            Doctor applicationDoctor = doctorRepository.findByEmail(username);

            return new org.springframework.security.core.userdetails.User(applicationDoctor.getEmail(), applicationDoctor.getPassword(), getAuthorities(applicationDoctor.getRole()));
        }else if(patientRepository.findByEmail(username) != null){
            Patient applicationPatient = patientRepository.findByEmail(username);
            return new org.springframework.security.core.userdetails.User(applicationPatient.getEmail(), applicationPatient.getPassword(),  getAuthorities(applicationPatient.getRole()));
        }else {
            throw new UsernameNotFoundException(username);
        }

    }

    private List<GrantedAuthority> getAuthorities(String privilege) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(privilege));

        return authorities;
    }


}