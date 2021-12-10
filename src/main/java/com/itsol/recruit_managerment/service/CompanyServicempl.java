package com.itsol.recruit_managerment.service;

import com.itsol.recruit_managerment.model.Company;
import com.itsol.recruit_managerment.repositories.CompanyRepo;
import com.itsol.recruit_managerment.vm.CompanyVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyServicempl implements CompanyService{
    @Autowired
    private CompanyRepo companyRepo;
    @Override
    public List<Company> getAllCompany() {
        return companyRepo.findAll();
    }


    public int update(CompanyVM companyVM , Long id){
        Company company = companyRepo.getCompanyById(id);
        try {
            company.setId(id);
            company.setName(companyVM.getName());
            company.setEmail(companyVM.getEmail());
            company.setTax_code(companyVM.getTax_code());
            company.setHot_line(companyVM.getHot_line());
            company.setHead_office(companyVM.getHead_office());
            company.setAvatar(companyVM.getAvatar());
            company.setBackdrop_img(companyVM.getBackdrop_img());
            company.setDescription(companyVM.getDescription());
            company.setLink_web(company.getLink_web());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                company.setDate_incoporation(sdf.parse(String.valueOf(companyVM.getDate_incoporation())));
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            try {
                company.setTax_date(sdf1.parse(String.valueOf(companyVM.getTax_date())));
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            company.setIs_delete(companyVM.getIs_delete());
            companyRepo.save(company);

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


}
