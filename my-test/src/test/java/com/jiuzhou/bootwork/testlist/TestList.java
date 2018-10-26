package com.jiuzhou.bootwork.testlist;

import com.alibaba.fastjson.JSON;
import com.jiuzhou.bootwork.beans.MenuDTO;
import com.jiuzhou.bootwork.beans.MenuListDTO;
import com.jiuzhou.bootwork.beans.Person;
import junit.framework.TestCase;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/02/02
 */
public class TestList extends TestCase {

    public void test_stream(){
        Person person = new Person();
        person.setId(1);
        person.setName("a");
        person.setAge(1);

        Person person2 = new Person();
        person2.setId(2);
        person2.setName("b");
        person2.setAge(2);

        Person person3 = new Person();
        person3.setId(3);
        person3.setName("c");
        person3.setAge(3);

        List<Person> list = new ArrayList<>();
        list.add(person);
        list.add(person2);
        list.add(person3);

        for (int i = 0; i < list.size() - 1; i++) {
            System.out.println("外");
            for (int j = i + 1; j < list.size(); j++) {
                System.out.println(list.get(i));
                System.out.println(list.get(j));
            }
        }
    }

    public void test_contains() {
        String[] strings = { "conDistanceRadius", "enableDensitySeeding", "conDensityRoughWeightLimit",
                        "conDensityRoughVolumeLimit", "conDensityRoughNeighborSizeLimit", "conDensityRadius",
                        "conDensitFinalWeightLimit", "minFillRateWeight", "minFillRateVolume", "conRadiusInZone1",
                        "conRadiusInZone2", "conRadiusInZone3", "conMaxNumOfAreas", "deliveryRadius", "originRadius" };
        List<String> list = new ArrayList<>();
        for (String string : strings) {
            list.add(string);
        }
        boolean conRadiusInZone3 = list.contains("conRadiusInZone3");
        System.out.println(conRadiusInZone3);
    }

    /**
     * list集合中可以存储null值，而且还可以存储多个null
     */
    public void testNull() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add(null);
        list.add(null);
        list.add(null);
        System.out.println(list);
    }

    /**
     * 集合迭代器，是可以删除集合中指定的元素的，一边遍历一边删除
     */
    public void test_remove() {
        List<String> list = new ArrayList<>();
        list.add("-1");

        ListIterator<String> stringListIterator = list.listIterator();
        while (stringListIterator.hasNext()) {
            String next = stringListIterator.next();
            if ("-1".equals(next)) {
                stringListIterator.remove();
            }
        }
        System.out.println(JSON.toJSONString(list));
    }

    public void test_sort() {
        List<String> keys = new ArrayList<>();
        keys.add("sdfg");
        keys.add("dg");
        keys.add("wef");
        keys.add("we");
        keys.add("weF");
        keys.add("weA");
        keys.add("hf");
        Collections.sort(keys);
        System.out.println(keys);
    }

    public void test_() {
        List<MenuDTO> menuDTOS = newParentMenus(2);

        int grade = 0;
        for (MenuDTO menuDTO : menuDTOS) {
            grade = menuDTO.getGrade() > grade ? menuDTO.getGrade() : grade;
        }

        for (int i = 0; i < grade - 1; i++) {
            menuDTOS = new ArrayList<>(getMenusWithChildren(menuDTOS));
        }

        menuDTOS = menuDTOS.parallelStream().filter(menuDTO -> menuDTO.getParentId() == 0).collect(Collectors.toList());

        menuDTOS = sortMenuDTOs(menuDTOS);

        List<MenuListDTO> menuListDTOS = dealMenus(menuDTOS);
        System.out.println(menuListDTOS);
    }

    /**
     * 创建0级别按钮
     *
     * @param count
     *
     * @return
     */
    private List<MenuDTO> newParentMenus(int count) {
        List<MenuDTO> menuDTOS = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            MenuDTO menuDTO = new MenuDTO();
            byte active = 1;
            byte grade = 0;
            Byte sort = new Byte("" + i);
            menuDTO.setActive(active);
            menuDTO.setComments("测试按钮");
            menuDTO.setGrade(grade);
            menuDTO.setId(i + 1);
            menuDTO.setMenuCode(UUID.randomUUID().toString());
            menuDTO.setParentId(0);
            menuDTO.setName("测试按钮" + (i + 1));
            menuDTO.setSort(sort);
            menuDTOS.add(menuDTO);
        }
        return menuDTOS;
    }

    /**
     * 获取所有按钮，这些按钮有子按钮集合
     *
     * @param menuDTOS
     *
     * @return
     */
    private Set<MenuDTO> getMenusWithChildren(List<MenuDTO> menuDTOS) {
        //所有子节点,进行分类,
        Map<Integer, List<MenuDTO>> collect = menuDTOS.parallelStream().filter(menuDTO -> menuDTO.getParentId() > 0)
                        .collect(Collectors.groupingBy(MenuDTO::getParentId));

        Set<MenuDTO> menuDTOsWithChildren = new HashSet<>();
        //父子关系
        menuDTOS.forEach(menuDTO -> {
            MenuDTO dto = new MenuDTO();
            dto.setName(menuDTO.getName());
            dto.setUri(menuDTO.getUri());
            dto.setSubMenus(collect.get(menuDTO.getId()));
            dto.setId(menuDTO.getId());
            dto.setParentId(menuDTO.getParentId());
            dto.setSort(menuDTO.getSort());
            menuDTOsWithChildren.add(dto);
        });

        return menuDTOsWithChildren;
    }

    private List<MenuDTO> sortMenuDTOs(List<MenuDTO> menuDTOS) {
        menuDTOS.forEach(menuDTO -> {
            if (!CollectionUtils.isEmpty(menuDTO.getSubMenus())) {
                menuDTO.setSubMenus(sortMenuDTOs(menuDTO.getSubMenus()));
            }
        });
        Stream<MenuDTO> sorted = menuDTOS.stream().sorted(Comparator.comparing(MenuDTO::getSort));
        List<MenuDTO> collect = sorted.collect(Collectors.toList());
        return collect;
    }

    private List<MenuListDTO> dealMenus(List<MenuDTO> menuDTOS) {
        List<MenuListDTO> menuListDTOS = new ArrayList<>();
        menuDTOS.forEach(menuDTO -> {
            MenuListDTO menuListDTO = new MenuListDTO();
            if (!CollectionUtils.isEmpty(menuDTO.getSubMenus())) {
                List<MenuListDTO> menuVOList = dealMenus(menuDTO.getSubMenus());
                BeanUtils.copyProperties(menuDTO, menuListDTO, "subMenus");
                menuListDTO.setSubMenus(menuVOList);
            } else {
                BeanUtils.copyProperties(menuDTO, menuListDTO);
            }
            menuListDTOS.add(menuListDTO);
        });
        return menuListDTOS;
    }

    public void test_subList() {
        List<String> list = new ArrayList<>();
        list.add("a");
        List<String> list1 = list.subList(0, 1);
        System.out.println(list1);
    }

}
