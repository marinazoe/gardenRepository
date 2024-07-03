package com.example.gardeningPlanner.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringUtilTest {

    @Test
    void isEmpty_NullString_ReturnsTrue() {
        assertTrue(StringUtil.isEmpty(null));
    }

    @Test
    void isEmpty_BlankString_ReturnsTrue() {
        assertTrue(StringUtil.isEmpty(""));
    }

    @Test
    void isEmpty_NonEmptyString_ReturnsFalse() {
        assertFalse(StringUtil.isEmpty("Hello"));
    }

    @Test
    void trim_NullString_ReturnsNull() {
        assertNull(StringUtil.trim(null));
    }

    @Test
    void trim_NonNullString_ReturnsTrimmedString() {
        assertEquals("Hello", StringUtil.trim("  Hello  "));
    }

    @Test 
    void isEmail_NullString_ReturnsFalse() {
        assertFalse(StringUtil.isEmail(null));
    }

    @Test 
    void isEmail_EmptyString_ReturnsFalse() {
        assertFalse(StringUtil.isEmail(""));
    }

    @Test
    void isEmail_InvalidFormat_ReturnsFalse() {
        // Arrange and Act
        String email1 = "test@tes.de@test";
        String email2 = "testtesttest";
        String email3 = "test.de";
        String email4 = "test@test@";
        String email5 = "@test@.de";

        // Assert
        assertFalse(StringUtil.isEmail(email1));
        assertFalse(StringUtil.isEmail(email2));
        assertFalse(StringUtil.isEmail(email3));
        assertFalse(StringUtil.isEmail(email4));
        assertFalse(StringUtil.isEmail(email5));
    }

    @Test
    void isEmail_ValidFormat_ReturnsTrue() {
        // Arrange and Act
        String email1 = "test@tes.de";
        String email2 = "test.test@t.de";
        String email3 = "te@st.de";
        String email4 = "test--hello@de.de";
        String email5 = "hallo-de.test@de-de";

        // Assert
        assertTrue(StringUtil.isEmail(email1));
        assertTrue(StringUtil.isEmail(email2));
        assertTrue(StringUtil.isEmail(email3));
        assertTrue(StringUtil.isEmail(email4));
        assertTrue(StringUtil.isEmail(email5));
    }

}
