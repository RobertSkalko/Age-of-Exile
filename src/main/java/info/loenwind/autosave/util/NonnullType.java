package info.loenwind.autosave.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An alternative to {@link javax.annotation.Nonnull} which works on type
 * parameters (J8 feature).
 */
@Target({ ElementType.TYPE_PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NonnullType {
}