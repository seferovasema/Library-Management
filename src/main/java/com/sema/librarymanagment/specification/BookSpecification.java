package com.sema.librarymanagment.specification;

import com.sema.librarymanagment.entity.Book;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification {

    public static Specification<Book> filterBooks(
            String title,
            String authorName,
            String category
    ) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                                "%" + title.toLowerCase() + "%"
                        )
                );
            }

            if (authorName != null && !authorName.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("author").get("name")),
                                "%" + authorName.toLowerCase() + "%"
                        )
                );
            }

            if (category != null && !category.isBlank()) {

                Join<Object, Object> categoryJoin = root.join("categories");

                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(categoryJoin.get("name")),
                                category.toLowerCase()
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}