package com.laurakovacic.sfgpetclinic.bootstrap;

import com.laurakovacic.sfgpetclinic.model.*;
import com.laurakovacic.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    public DataInitializer(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();

        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        Specialty savedRadiology = specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        Specialty savedSurgery = specialtyService.save(surgery);       // we get ID value when we persist to the map

        Specialty dentistry = new Specialty();
        dentistry.setDescription("Dentistry");
        Specialty savedDentistry = specialtyService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Laura");
        owner1.setLastName("Kovacic");
        owner1.setAddress("12A Bickerer");
        owner1.setCity("Miami");
        owner1.setTelephone("12332189732");
        ownerService.save(owner1);

        Pet laurasPet = new Pet();
        laurasPet.setOwner(owner1);
        laurasPet.setPetType(savedDogPetType);
        laurasPet.setBirthDate(LocalDate.now());
        laurasPet.setName("Buck");

        owner1.getPets().add(laurasPet);
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Luka");
        owner2.setLastName("Ronin");
        owner2.setAddress("12A Bickerer");
        owner2.setCity("Miami");
        owner2.setTelephone("12872312378");
        ownerService.save(owner2);

        Pet lukasPet = new Pet();
        lukasPet.setOwner(owner2);
        lukasPet.setPetType(savedCatPetType);
        lukasPet.setBirthDate(LocalDate.now());
        lukasPet.setName("Freya");

        owner2.getPets().add(lukasPet);
        ownerService.save(owner2);

        Visit catVisit = new Visit();
        catVisit.setPet(lukasPet);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Regular check");
        visitService.save(catVisit);

        Visit dogVisit = new Visit();
        dogVisit.setPet(laurasPet);
        dogVisit.setDate(LocalDate.now());
        dogVisit.setDescription("Regular POSTOP check");
        visitService.save(dogVisit);

        System.out.println("Loaded owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Domagoj");
        vet1.setLastName("Petrovic");
        vet1.getSpecialties().add(savedRadiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Borna");
        vet2.setLastName("Simunovic");
        vet2.getSpecialties().add(savedSurgery);

        vetService.save(vet2);
        System.out.println("Loaded vets...");
    }
}
