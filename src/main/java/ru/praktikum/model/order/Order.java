package ru.praktikum.model.order;

import lombok.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Order {
private List<String> ingredients;
}
