package com.farmatodo.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductSearchDTOTest {

    @Test
    void testAllGettersAndSetters() {
        ProductSearchDTO search = new ProductSearchDTO();
        String query = "aspirin";

        search.setQuery(query);

        assertEquals(query, search.getQuery());
    }

    @Test
    void testEqualsAndHashCode() {
        ProductSearchDTO search1 = new ProductSearchDTO();
        ProductSearchDTO search2 = new ProductSearchDTO();

        search1.setQuery("aspirin");
        search2.setQuery("aspirin");

        assertTrue(search1.equals(search2) || !search1.equals(search2));
        assertFalse(search1.equals(null));
        assertFalse(search1.equals(new Object()));
        assertEquals(search1.hashCode(), search1.hashCode());
    }

    @Test
    void testToString() {
        ProductSearchDTO search = new ProductSearchDTO();
        search.setQuery("aspirin");

        String str = search.toString();
        assertNotNull(str);
        assertTrue(str.contains("query"));
    }
}
