package tech.hiphone.rbac.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.hiphone.rbac.domain.Navigation;
import tech.hiphone.rbac.domain.NavigationRoleRelation;
import tech.hiphone.rbac.domain.Staff;
import tech.hiphone.rbac.domain.StaffRoleRelation;
import tech.hiphone.rbac.domain.id.NavigationRoleId;
import tech.hiphone.rbac.repository.NavigationRepository;
import tech.hiphone.rbac.repository.NavigationRoleRelationRepository;
import tech.hiphone.rbac.repository.StaffRoleRelationRepository;
import tech.hiphone.rbac.service.dto.NavigationTreeDTO;

@Service
@Transactional
public class NavigationService {

    @Autowired
    private NavigationRepository navigationRepository;

    @Autowired
    private NavigationRoleRelationRepository navigationRoleRelationRepository;

    @Autowired
    private StaffRoleRelationRepository staffRoleRelationRepository;

    public List<NavigationTreeDTO> getAllTrees() {
        List<Navigation> children = navigationRepository.findAllByParent(null);
        List<NavigationTreeDTO> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(children)) {
            for (Navigation child : children) {
                list.add(getNavTree(child));
            }
        }
        return list;
    }

    public NavigationTreeDTO getNavTree(Navigation node) {
        NavigationTreeDTO tree = new NavigationTreeDTO();
        tree.set(node);

        List<Navigation> children = navigationRepository.findAllByParent(node);
        List<NavigationTreeDTO> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(children)) {
            for (Navigation child : children) {
                list.add(getNavTree(child));
            }
        }
        tree.setChildren(list);
        return tree;
    }

    // 获取和角色相关的导航
    public List<NavigationTreeDTO> getAllRoleNavTree(Staff staff) {
        List<StaffRoleRelation> staffRoleRelationList = staffRoleRelationRepository.findAllByIdStaff(staff);

        Set<String> navigationIdSet = new HashSet<>();
        for (StaffRoleRelation staffRoleRelation : staffRoleRelationList) {
            List<NavigationRoleRelation> navigationRoleRelationList = navigationRoleRelationRepository
                    .findAllByIdRole(staffRoleRelation.getId().getRole());
            navigationIdSet.addAll(navigationRoleRelationList.stream().map(NavigationRoleRelation::getId)
                    .map(NavigationRoleId::getNavigation).map(Navigation::getId).collect(Collectors.toSet()));
        }
        return getRoleNavTree(getAllTrees(), navigationIdSet);
    }

    public List<NavigationTreeDTO> getRoleNavTree(List<NavigationTreeDTO> navigationTreeList,
            Set<String> navigationIdSet) {

        if (CollectionUtils.isEmpty(navigationTreeList)) {
            return navigationTreeList;
        }

        List<NavigationTreeDTO> newNavigationTreeList = new ArrayList<>();
        for (NavigationTreeDTO navigationTreeDTO : navigationTreeList) {
            if (navigationIdSet.contains(navigationTreeDTO.getId())) {
                navigationTreeDTO.setChildren(getRoleNavTree(navigationTreeDTO.getChildren(), navigationIdSet));
                newNavigationTreeList.add(navigationTreeDTO);
            }
        }
        return newNavigationTreeList;
    }

}
