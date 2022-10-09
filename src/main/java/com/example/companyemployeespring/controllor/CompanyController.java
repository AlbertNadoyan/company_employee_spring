package com.example.companyemployeespring.controllor;

import com.example.companyemployeespring.entity.Address;
import com.example.companyemployeespring.entity.Company;
import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.repository.CompanyRepository;
import com.example.companyemployeespring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyRepository companyRepository;

    private final EmployeeRepository employeeRepository;

    @GetMapping("/companies")
    public String companies(ModelMap modelMap){
        List<Company> companyList = companyRepository.findAll();
        modelMap.addAttribute("companies", companyList);
        return "companies";
    }

    @GetMapping("/company/add")
    public String addCompanyPage(ModelMap modelMap){
        Address[] values = Address.values();
        modelMap.addAttribute("addresses", values);
        return "addCompany";
    }

    @PostMapping("/company/add")
    public String addCompany(@ModelAttribute Company company){
        companyRepository.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/company/delete/{id}")
    public String deleteCompany(@PathVariable("id") int id){
        companyRepository.deleteById(id);
        return "redirect:/companies";
    }
}
