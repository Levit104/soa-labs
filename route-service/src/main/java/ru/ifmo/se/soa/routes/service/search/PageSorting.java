package ru.ifmo.se.soa.routes.service.search;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.ifmo.se.soa.routes.dto.search.PageSpec;
import ru.ifmo.se.soa.routes.dto.search.SortSpec;
import ru.ifmo.se.soa.routes.exception.InvalidSearchSpecsException;
import ru.ifmo.se.soa.routes.util.ValidationUtils;

import java.util.List;

public class PageSorting {
    private PageSorting() {
    }

    private static final String DEFAULT_SORT_PROPERTY = "id";
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public static Pageable sortPage(List<SortSpec> sorts, PageSpec page) {
        var sortOrders = getSortOrders(sorts);
        var pageNumber = getPageNumber(page);
        var pageSize = getPageSize(page);

        return PageRequest.of(pageNumber, pageSize, Sort.by(sortOrders));
    }

    public static Pageable sortPage() {
        return PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, Sort.by(defaultSort()));
    }

    private static List<Sort.Order> getSortOrders(List<SortSpec> sorts) {
        if (sorts == null || sorts.isEmpty()) {
            return defaultSort();
        }

        if (hasDuplicates(sorts)) {
            throw new InvalidSearchSpecsException("Дублирующиеся параметры сортировки");
        }

        var sortOrders = sorts.stream()
                .filter(PageSorting::shouldApplySort)
                .map(PageSorting::toSortOrder)
                .toList();

        if (sortOrders.isEmpty()) {
            throw new InvalidSearchSpecsException("Некорректные параметры сортировки");
        }

        return sortOrders;
    }

    private static List<Sort.Order> defaultSort() {
        return List.of(Sort.Order.by(DEFAULT_SORT_PROPERTY).with(DEFAULT_SORT_DIRECTION));
    }

    private static boolean hasDuplicates(List<SortSpec> sorts) {
        boolean duplicateFields = sorts.stream().map(SortSpec::field).distinct().toList().size() != sorts.size();
        return ValidationUtils.hasDuplicates(sorts) || duplicateFields;
    }

    private static boolean shouldApplySort(SortSpec sort) {
        return ValidationUtils.notBlank(sort.field()) && ValidationUtils.notBlank(sort.order());
    }

    private static Sort.Order toSortOrder(SortSpec sort) {
        return Sort.Order.by(sort.field()).with(Sort.Direction.valueOf(sort.order()));
    }

    private static int getPageNumber(PageSpec page) {
        return (page == null || page.number() == null) ? DEFAULT_PAGE_NUMBER : page.number();
    }

    private static int getPageSize(PageSpec page) {
        return (page == null || page.size() == null) ? DEFAULT_PAGE_SIZE : page.size();
    }
}
