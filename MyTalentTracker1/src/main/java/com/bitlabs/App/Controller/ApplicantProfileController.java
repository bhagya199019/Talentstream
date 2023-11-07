package com.bitlabs.App.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bitlabs.App.Entity.ApplicantProfile;
import com.bitlabs.App.Entity.JobApplicant;

import com.bitlabs.App.Repository.JobApplicantRepository;
import com.bitlabs.App.Service.ApplicantProfileService;

@CrossOrigin("*")
@RestController
public class ApplicantProfileController {

	@Autowired
 private JobApplicantRepository jobApplicantRepository;
	
	@Autowired
 private ApplicantProfileService applicantProfileService;
	
	
      @PostMapping("/applicant/createProfile/{applicantId}")
  public ResponseEntity<ApplicantProfile>createOrUpdateProfile(@RequestBody ApplicantProfile applicantProfile,
		  @PathVariable long applicantId){
	
	JobApplicant jobApplicant=jobApplicantRepository.getJobApplicantById(applicantId);
	
	if(jobApplicant==null) {
		return ResponseEntity.notFound().build();
	}
	else {
		applicantProfile.setApplicant(jobApplicant);
		ApplicantProfile createdProfile=applicantProfileService.createOrUpdateProfile(applicantProfile);
		return ResponseEntity.ok(createdProfile);
		}
      }
	
	/* @GetMapping("/applicant/getapplicantdetails/{profileid}")
	   public ResponseEntity<ApplicantProfile> getApplicantProfileById(@PathVariable int profileid) {
	       ApplicantProfile applicantProfile = applicantProfileService.viewApplicantById(profileid);
	       if (applicantProfile!= null) {
	           return ResponseEntity.ok(applicantProfile);
	       } else {
	           return ResponseEntity.notFound().build();
	       }
	   }
	 
	 @GetMapping("/applicant/getapplicantProfileDetails/{profileid}")
	    public ResponseEntity<ApplicantProfile> getRecruiterProfileById(@PathVariable int profileid) {
	        Optional<ApplicantProfile> applicantProfile =applicantProfileService.viewApplicantById(profileid);
	        return applicantProfile.map(profile -> new ResponseEntity<>(profile, HttpStatus.OK))
	                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }*/
      
      @GetMapping("/applicant/getapplicantProfileDetails/{profileid}")
      public ResponseEntity<Optional<ApplicantProfile>> getApplicantProfileDetails(@PathVariable int profileid) {
          try {
              Optional<ApplicantProfile> applicantProfile = applicantProfileService.viewApplicantById(profileid);
              System.out.println(applicantProfile);
              if (applicantProfile.isPresent()) {
                  return ResponseEntity.ok(applicantProfile); // Profile found, return 200 OK
              } else {
                  return ResponseEntity.notFound().build(); // Profile not found, return 404 Not Found
              }
          } catch (Exception e) {
              // Handle any exceptions, e.g., database errors
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
          }
      }
	      
	 
	 @DeleteMapping("/applicant/deletedetails/{applicantId}")
	       public ResponseEntity<Void> deleteApplicantProfile(@PathVariable int applicantId) {
	           applicantProfileService.deleteApplicantById(applicantId);
	           return ResponseEntity.noContent().build();
	       }
		
}
	
	
	
	
	


