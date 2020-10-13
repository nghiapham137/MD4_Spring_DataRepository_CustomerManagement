package controller;

import model.Customer;
import model.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.CustomerService;
import service.ProvinceService;
@RequestMapping("/provinces")
@Controller
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String listProvince(Model model) {
        Iterable<Province> provinces = provinceService.findAll();
        model.addAttribute("provinces", provinces);
        return "/province/list";
    }

    @GetMapping("/create-province")
    public String showCreateProvinceForm(Model model) {
        model.addAttribute("province", new Province());
        return "/province/create";
    }

    @PostMapping("/create-province")
    public String createProvince(@ModelAttribute("province") Province province, Model model) {
        provinceService.save(province);
        model.addAttribute("province", new Province());
        model.addAttribute("message","New province was created successfully");
        return "/province/create";
    }

    @GetMapping("/edit/{id}")
    public String showEditProvinceForm(@PathVariable("id") Long id, Model model) {
        Province province = provinceService.findById(id);
        if (province != null) {
            model.addAttribute("province", province);
            return "/province/edit";
        }else {
            return "error404";
        }

    }

    @PostMapping("/edit")
    public String editProvince(@ModelAttribute("province")Province province, Model model) {
        provinceService.save(province);
        model.addAttribute("province", province);
        model.addAttribute("message","Province update successfully");
        return "/province/edit";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        Province province = provinceService.findById(id);
        if (province != null) {
            model.addAttribute("province", province);
            return "/province/delete";
        }else {
            return "error404";
        }
    }

    @PostMapping("/delete")
    public String deleteProvince(@ModelAttribute("province") Province province) {
        provinceService.remove(province.getId());
        return "redirect:/provinces";
    }

    @GetMapping("/view/{id}")
    public String viewProvince(@PathVariable("id") Long id, Model model) {
        Province province = provinceService.findById(id);
        if (province != null) {
            Iterable<Customer> customers = customerService.findAllByProvince(province);
            model.addAttribute("province", province);
            model.addAttribute("customers", customers);
            return "/province/view";
        }else {
            return "error404";
        }
    }


}
