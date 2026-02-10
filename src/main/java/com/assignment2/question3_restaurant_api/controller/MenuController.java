package com.assignment2.question3_restaurant_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assignment2.question3_restaurant_api.model.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private List<MenuItem> menuItems;

    public MenuController() {
        menuItems = new ArrayList<>();

        menuItems.add(new MenuItem(1L, "Spring Rolls", "Crispy vegetable rolls", 4.5, "Appetizer", true));
        menuItems.add(new MenuItem(2L, "Chicken Wings", "Spicy grilled wings", 6.0, "Appetizer", true));

        menuItems.add(new MenuItem(3L, "Beef Burger", "Burger with cheese and fries", 8.5, "Main Course", true));
        menuItems.add(new MenuItem(4L, "Grilled Fish", "Fish with lemon sauce", 10.0, "Main Course", false));

        menuItems.add(new MenuItem(5L, "Chocolate Cake", "Rich chocolate dessert", 5.0, "Dessert", true));
        menuItems.add(new MenuItem(6L, "Ice Cream", "Vanilla ice cream", 3.5, "Dessert", true));

        menuItems.add(new MenuItem(7L, "Coffee", "Hot black coffee", 2.0, "Beverage", true));
        menuItems.add(new MenuItem(8L, "Orange Juice", "Fresh juice", 2.5, "Beverage", false));
    }

    // get all menu items
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menuItems);
    }

    // get menu item by id
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {

        return menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // get menu items by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getByCategory(@PathVariable String category) {

        List<MenuItem> result = menuItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // get available menu items
    @GetMapping("/available")
    public ResponseEntity<List<MenuItem>> getAvailableItems(
            @RequestParam boolean available) {

        List<MenuItem> result = menuItems.stream()
                .filter(item -> item.isAvailable() == available)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // search menu items by name
    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchByName(
            @RequestParam String name) {

        List<MenuItem> result = menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // add new menu item
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {

        menuItems.add(menuItem);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuItem);
    }

    // update menu item
    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {

        for (MenuItem item : menuItems) {
            if (item.getId().equals(id)) {
                item.setAvailable(!item.isAvailable());
                return ResponseEntity.ok(item);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // delete menu item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {

        boolean removed = menuItems.removeIf(item -> item.getId().equals(id));

        if (removed) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
