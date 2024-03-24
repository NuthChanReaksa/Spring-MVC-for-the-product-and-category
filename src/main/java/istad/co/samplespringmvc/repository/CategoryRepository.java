package istad.co.samplespringmvc.repository;


import istad.co.samplespringmvc.model.Category;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Getter
@Repository
public class CategoryRepository {
    private final List<Category> allCategories = new ArrayList<>(){{
        add(Category.builder()
                .id(1)
                .title("Electronic")
                .description("All electronic compartment!")
                .build());
        add(Category.builder()
                .id(2)
                .title("Food")
                .description("Stuff that you can eat!")
                .build());
    }};

    public Category addCategory(Category category){
        allCategories.add(category);
        return category;
    }
    public void updateCategory (Category category) {
        int index = allCategories.indexOf(
                allCategories.stream()
                .filter(cate -> cate.getId() == category.getId())
                .findFirst()
                .orElse(null)
        );
        allCategories.set(index, category);

    }
    public void deleteCategories(int id){
        int index = allCategories.indexOf(
                allCategories.stream()
                        .filter(cate -> cate.getId() == id )
                        .findFirst()
                        .orElse(null)
        );
        allCategories.remove(index);
    }


}

