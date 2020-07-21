package info.loenwind.autosave.util;

import java.lang.annotation.*;

@Target({
    ElementType.TYPE_PARAMETER,
    ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NullableType {
}