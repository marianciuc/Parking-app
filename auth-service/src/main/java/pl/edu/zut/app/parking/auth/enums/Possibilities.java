package pl.edu.zut.app.parking.auth.enums;


import lombok.Getter;


// При добавлении новых прав: Каждый раз, когда вы добавляете новые возможности, вам нужно обновить структуру данных для всех пользователей, чтобы отразить новые права.
// При изменении структуры прав: Если права изменяются (например, добавляются новые), это потребует обновления всех записей в базе данных, чтобы пользователи имели новые данные.

/**
 * Enum class for user possibilities
 */
@Getter
public enum Possibilities {
    ADMIN(1, "Can do everything"),
    CHANGE_PASSWORD(1, "Can change password"),
    LOGIN_IN_ADMIN_PANEL(2, "Can login in admin panel"),
    LOGIN_IN_CUSTOMER_PANEL(3, "Can login in customer panel"),
    LOGIN_IN_ORGANIZATION_PANEL(4, "Can login in organization panel"),
    LOGIN_IN_PARKING_PANEL(5, "Can login in parking panel");

    private final int id;
    private final String description;

    Possibilities(int i, String description) {
        this.id = i;
        this.description = description;
    }
}