package com.tuban.midterm.controller;

import com.tuban.midterm.model.Contact;
import com.tuban.midterm.service.GooglePeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private GooglePeopleService peopleService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/contacts";
    }

    @GetMapping("/contacts")
    public String getContacts(Model model, @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) {
        try {
            model.addAttribute("contacts", peopleService.getContacts(authorizedClient));
            model.addAttribute("contact", new Contact()); // Add empty contact for form binding
            return "contacts";
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching contacts");
            model.addAttribute("contact", new Contact());
            return "contacts";
        }
    }

    @PostMapping("/contacts/add")
    public String addContact(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(value = "email", required = false) String[] emails,
            @RequestParam(value = "phoneNumber", required = false) String[] phoneNumbers,
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient, 
            RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Add Contact - Input data:");
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Email Array: " + (emails != null ? Arrays.toString(emails) : "null"));
            System.out.println("Phone Array: " + (phoneNumbers != null ? Arrays.toString(phoneNumbers) : "null"));

            // Ensure we have at least one email and phone number
            if (emails == null || emails.length == 0 || phoneNumbers == null || phoneNumbers.length == 0) {
                redirectAttributes.addFlashAttribute("error", "At least one email and phone number are required");
                return "redirect:/contacts";
            }

            Contact contact = new Contact(
                firstName,
                lastName,
                Arrays.asList(emails),
                Arrays.asList(phoneNumbers),
                null // resourceName will be assigned by Google
            );
            
            peopleService.createContact(authorizedClient, contact);
            redirectAttributes.addFlashAttribute("success", "Contact added successfully");
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            if (errorMsg.contains("INVALID_ARGUMENT")) {
                errorMsg = "Invalid contact information provided. Please check all fields and try again.";
            }
            System.err.println("Error in addContact: " + errorMsg);
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error adding contact: " + errorMsg);
        }
        return "redirect:/contacts";
    }

    @PostMapping("/contacts/update")
    public String updateContact(
            @RequestParam String resourceName,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(value = "email", required = false) String[] emails,
            @RequestParam(value = "phoneNumber", required = false) String[] phoneNumbers,
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
            RedirectAttributes redirectAttributes) {
        try {
            // Debug logging
            System.out.println("Update Contact - Input data:");
            System.out.println("Resource Name: " + resourceName);
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Email Array: " + (emails != null ? Arrays.toString(emails) : "null"));
            System.out.println("Phone Array: " + (phoneNumbers != null ? Arrays.toString(phoneNumbers) : "null"));

            // Ensure we have at least one email and phone number
            if (emails == null || emails.length == 0 || phoneNumbers == null || phoneNumbers.length == 0) {
                redirectAttributes.addFlashAttribute("error", "At least one email and phone number are required");
                return "redirect:/contacts";
            }
            
            Contact contact = new Contact(
                firstName, 
                lastName, 
                Arrays.asList(emails),
                Arrays.asList(phoneNumbers),
                resourceName
            );

            System.out.println("Contact object created:");
            System.out.println("Email list: " + contact.getEmail());
            System.out.println("Phone list: " + contact.getPhoneNumber());

            peopleService.updateContact(authorizedClient, resourceName, contact);
            redirectAttributes.addFlashAttribute("success", "Contact updated successfully");
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            if (errorMsg.contains("NOT_FOUND")) {
                errorMsg = "Contact not found or was deleted. Please refresh the page and try again.";
            } else if (errorMsg.contains("INVALID_ARGUMENT")) {
                errorMsg = "Invalid contact information provided. Please check all fields and try again.";
            }
            System.err.println("Error in updateContact: " + errorMsg);
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error updating contact: " + errorMsg);
        }
        return "redirect:/contacts";
    }

    @PostMapping("/contacts/delete")
    public String deleteContact(
            @RequestParam String resourceName,
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient,
            RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Delete Contact - Resource Name: " + resourceName);
            peopleService.deleteContact(authorizedClient, resourceName);
            redirectAttributes.addFlashAttribute("success", "Contact deleted successfully");
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            if (errorMsg.contains("NOT_FOUND")) {
                errorMsg = "Contact not found or was already deleted. Please refresh the page.";
            }
            System.err.println("Error in deleteContact: " + errorMsg);
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error deleting contact: " + errorMsg);
        }
        return "redirect:/contacts";
    }
}
