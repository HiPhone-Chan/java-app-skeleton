package tech.hiphone.rbac.service;

import java.util.Optional;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.commons.constants.AuthoritiesConstants;
import tech.hiphone.commons.domain.User;
import tech.hiphone.commons.service.UserService;
import tech.hiphone.rbac.domain.Staff;
import tech.hiphone.rbac.repository.StaffRepository;
import tech.hiphone.rbac.service.dto.AdminStaffDTO;

@Service
@Transactional
public class StaffService {

    private static final Logger log = LoggerFactory.getLogger(StaffService.class);

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private UserService userService;

    public Staff createStaff(AdminStaffDTO staffDTO) {
        staffDTO.setAuthorities(SetUtils.hashSet(AuthoritiesConstants.MANAGER));
        User user = userService.createUser(staffDTO);
        Staff staff = new Staff();
        staff.setId(staffDTO.getStaffId());
        String staffId = staffDTO.getStaffId();
        if (StringUtils.isEmpty(staffId)) {
            staffId = generateStaffId();
        }
        staff.setUser(user);
        return staffRepository.save(staff);
    }

    public Page<AdminStaffDTO> getStaffs(Specification<Staff> spec, Pageable pageable) {
        return staffRepository.findAll(spec, pageable).map(staff -> {
            AdminStaffDTO adminStaffDTO = new AdminStaffDTO(staff.getUser());
            adminStaffDTO.setStaffId(staff.getId());
            return adminStaffDTO;
        });
    }

    public Optional<Staff> getCurrentStaff() {
        User user = userService.getUserWithAuthorities().orElseThrow();
        return staffRepository.findOneByUser(user);
    }

    public Optional<Staff> getCurrentStaffByLogin(String login) {
        User user = userService.getUserWithAuthoritiesByLogin(login).orElseThrow();
        return staffRepository.findOneByUser(user);
    }

    public String generateStaffId() {
        String id = RandomStringUtils.randomNumeric(10);
        int count = 0;
        while (staffRepository.existsById(id)) {
            id = RandomStringUtils.randomNumeric(10);
            if (count++ > 30) {
                log.warn("warning generate Teacher Id failed too many times.");
            }
        }
        return id;
    }

    public void deleteStaff(String login) {
        User user = userService.getUserWithAuthoritiesByLogin(login).orElseThrow();

        staffRepository.deleteByUser(user);
        userService.deleteUser(login);
    }
}
