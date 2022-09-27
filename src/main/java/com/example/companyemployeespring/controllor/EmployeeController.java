package com.example.companyemployeespring.controllor;

import com.example.companyemployeespring.entity.Company;
import com.example.companyemployeespring.entity.Employee;
import com.example.companyemployeespring.entity.Position;
import com.example.companyemployeespring.repository.CompanyRepository;
import com.example.companyemployeespring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    @Value("${project.images.path}")
    private String folderPath;

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public String employees(ModelMap modelMap){
        List<Employee> employeesList = employeeRepository.findAll();
        List<Company> companyList = companyRepository.findAll();
        modelMap.addAttribute("employees", employeesList);
        modelMap.addAttribute("companies", companyList);
        return "employees";
    }

    @GetMapping("/employee/add")
    public String addEmployeePage(ModelMap modelMap){
        List<Company> companyList = companyRepository.findAll();
        Position[] values = Position.values();
        modelMap.addAttribute("company", companyList);
        modelMap.addAttribute("positions", values);
        return "addEmployee";
    }

    @PostMapping("/employee/add")
    public String addEmployee(@ModelAttribute Employee employee, @RequestParam("employeeImage") MultipartFile file) throws IOException {
        if(file.isEmpty() && file.getSize() > 0){
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + fileName);
            file.transferTo(newFile);
            employee.setProfilePic(fileName);
        }
        Company company = companyRepository.getById(employee.getCompany().getId());
        company.setSize(company.getSize() + 1);
        companyRepository.save(company);
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/employee/getImage")
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    @GetMapping("employee/delete/{id}")
    public String deleteEmployee(@ModelAttribute Company company,  Employee employee, @PathVariable("id") int id){
        if(company.getName().equals(employee.getCompany().getName())){
            company.setSize(company.getSize() - 1);
            employeeRepository.deleteById(id);
            companyRepository.save(company);
        }
        return "redirect:/employees";
    }

}
