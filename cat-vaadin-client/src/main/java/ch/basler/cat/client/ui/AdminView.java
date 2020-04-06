package ch.basler.cat.client.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Admin view that is registered dynamically on admin user login.
 * <p>
 * Allows CRUD operations for the book categories.
 */
public class AdminView extends VerticalLayout {

    public static final String VIEW_NAME = "Admin";

//    private final IronList<Category> categoriesListing;
//    private final ListDataProvider<Category> dataProvider;
//    private final Button newCategoryButton;

    public AdminView() {
//        categoriesListing = new IronList<>();
//
//        dataProvider = new ListDataProvider<Category>(
//                new ArrayList<>(DataService.get().getAllCategories()));
//        categoriesListing.setDataProvider(dataProvider);
//        categoriesListing.setRenderer(
//                new ComponentRenderer<>(this::createCategoryEditor));
//
//        newCategoryButton = new Button("Add New Category", event -> {
//            final Category category = new Category();
//            dataProvider.getItems().add(category);
//            dataProvider.refreshAll();
//        });
//        newCategoryButton.setDisableOnClick(true);

//        add(new H2("Hello Admin"), new H4("Edit Categories"), newCategoryButton,
//                categoriesListing);

        add(new H2("Welcome to the Admin section"));
    }

//    private Component createCategoryEditor(Category category) {
//        final TextField nameField = new TextField();
//        if (category.getId() < 0) {
//            nameField.focus();
//        }

//        final Button deleteButton = new Button(
//                VaadinIcon.MINUS_CIRCLE_O.create(), event -> {
//
//                    // Ask for confirmation before deleting stuff
//                    final ConfirmDialog dialog = new ConfirmDialog(
//                            "Please confirm",
//                            "Are you sure you want to delete the category? Books in this category will not be deleted.",
//                            "Delete", () -> {
//                                DataService.get()
//                                        .deleteCategory(category.getId());
//                                dataProvider.getItems().remove(category);
//                                dataProvider.refreshAll();
//                                Notification.show("Category Deleted.");
//                            });
//
//                    dialog.open();
//
//                });
//        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
//
//        final BeanValidationBinder<Category> binder = new BeanValidationBinder<>(
//                Category.class);
//        binder.forField(nameField).bind("name");
//        binder.setBean(category);
//        binder.addValueChangeListener(event -> {
//            if (binder.isValid()) {
//                DataService.get().updateCategory(category);
//                deleteButton.setEnabled(true);
//                newCategoryButton.setEnabled(true);
//                Notification.show("Category Saved.");
//            }
//        });
//        deleteButton.setEnabled(category.getId() > 0);
//
//        final HorizontalLayout layout = new HorizontalLayout(nameField,
//                deleteButton);
//        layout.setFlexGrow(1);
//        return layout;
//    }

}
