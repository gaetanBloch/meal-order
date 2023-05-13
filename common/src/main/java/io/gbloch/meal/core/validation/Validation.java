/*
 * Copyright (c) 2023 Gaëtan Bloch and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.gbloch.meal.core.validation;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * This class provides utilities for input validations.
 *
 * <p>
 * It is designed to validate domain input, if you want to validate application
 * input it's better to stick to
 * BeanValidation to get all errors at once and internationalized error messages.
 * </p>
 *
 * <p>
 * The main goal of this class is to ensure some basic type validation in your classes.
 * If you have to do business
 * related validation you should create your own exception and code dedicated to that check
 * </p>
 *
 * @author Gaëtan Bloch
 * <br>Created on 07/05/2023
 */
@SuppressWarnings({ "NullAway", "filelength", "methodcount", "classdataabstractioncoupling" })
public final class Validation {

    private Validation() {
        // Prevent instantiability
        throw new UnsupportedOperationException();
    }

    /**
     * Ensure that the input is not null
     *
     * @param field name of the field to check (will be displayed in exception message)
     * @param input input to check
     * @throws MissingMandatoryValueException if the input is null
     */
    public static void notNull(String field, Object input) {
        if (input == null) {
            throw MissingMandatoryValueException.forNullValue(field);
        }
    }

    /**
     * Ensure that the value is not blank (null, empty or only whitespace)
     *
     * @param field name of the field to check (will be displayed in exception message)
     * @param input input to check
     * @throws MissingMandatoryValueException if the input is blank
     */
    public static void notBlank(String field, String input) {
        Validation.field(field, input).notBlank();
    }

    /**
     * Ensure that the given collection is not empty
     *
     * @param field      name of the field to check (will be displayed in exception message)
     * @param collection collection to check
     * @throws MissingMandatoryValueException if the collection is null or empty
     */
    public static void notEmpty(String field, Collection<?> collection) {
        field(field, collection).notEmpty();
    }

    /**
     * Ensure that the given map is not empty
     *
     * @param field name of the field to check (will be displayed in exception message)
     * @param map   map to check
     * @throws MissingMandatoryValueException if the map is null or empty
     */
    public static void notEmpty(String field, Map<?, ?> map) {
        notNull(field, map);

        if (map.isEmpty()) {
            throw MissingMandatoryValueException.forEmptyValue(field);
        }
    }

    /**
     * Create a fluent validator for {@link String}
     *
     * <p>
     * Usage:
     * </p>
     *
     * <pre>
     * <code>
     * Validation.field("name", name)
     *   .notBlank()
     *   .maxLength(150);
     * </code>
     * </pre>
     *
     * @param field name of the field to check (will be displayed in exception message)
     * @param input string to check
     * @return A {@link StringValidator} for this field and value
     */
    public static StringValidator field(String field, String input) {
        return new StringValidator(field, input);
    }

    /**
     * Create a fluent validator for Integer values (int and {@link Integer})
     *
     * <p>
     * Usage:
     * </p>
     *
     * <pre>
     * <code>
     * Validation.field("age", age)
     *   .min(0)
     *   .max(150);
     * </code>
     * </pre>
     *
     * @param field name of the field to check (will be displayed in exception message)
     * @param input value to check
     * @return An {@link IntegerValidator} for this field and value
     */
    public static IntegerValidator field(String field, Integer input) {
        return new IntegerValidator(field, input);
    }

    /**
     * Create a fluent validator for Long values (long and {@link Long})
     *
     * <p>
     * Usage:
     * </p>
     *
     * <pre>
     * <code>
     * Validation.field("duration", duration)
     *   .min(100)
     *   .max(500_000);
     * </code>
     * </pre>
     *
     * @param field name of the field to check (will be displayed in exception message)
     * @param input value to check
     * @return An {@link LongValidator} for this field and value
     */
    public static LongValidator field(String field, Long input) {
        return new LongValidator(field, input);
    }

    /**
     * Create a fluent validator for Float values (float and {@link Float})
     *
     * <p>
     * Usage:
     * </p>
     *
     * <pre>
     * <code>
     * Validation.field("rate", rate)
     *   .min(0)
     *   .max(1);
     * </code>
     * </pre>
     *
     * @param field name of the field to check (will be displayed in exception message)
     * @param input value to check
     * @return An {@link DoubleValidator} for this field and value
     */
    public static FloatValidator field(String field, Float input) {
        return new FloatValidator(field, input);
    }

    /**
     * Create a fluent validator for Double values (double and {@link Double})
     *
     * <p>
     * Usage:
     * </p>
     *
     * <pre>
     * <code>
     * Validation.field("rate", rate)
     *   .min(0)
     *   .max(1);
     * </code>
     * </pre>
     *
     * @param field name of the field to check (will be displayed in exception message)
     * @param input value to check
     * @return An {@link DoubleValidator} for this field and value
     */
    public static DoubleValidator field(String field, Double input) {
        return new DoubleValidator(field, input);
    }

    /**
     * Create a fluent validator for {@link BigDecimal} values
     *
     * <p>
     * Usage:
     * </p>
     *
     * <pre>
     * <code>
     * Validation.field("rate", rate)
     *   .min(0)
     *   .max(1);
     * </code>
     * </pre>
     *
     * @param field name of the field to check (will be displayed in exception message)
     * @param input value to check
     * @return An {@link BigDecimalValidator} for this field and value
     */
    public static BigDecimalValidator field(String field, BigDecimal input) {
        return new BigDecimalValidator(field, input);
    }

    /**
     * Create a fluent validator for {@link Collection}
     *
     * <p>
     * Usage:
     * </p>
     *
     * <pre>
     * <code>
     * Validation.field("name", name)
     *  .notEmpty()
     *  .maxSize(150);
     * </code>
     * </pre>
     *
     * @param field name of the field to check (will be displayed in exception message)
     * @param input collection to check
     * @return A {@link CollectionValidator} for this field and value
     */
    public static <T> CollectionValidator<T> field(String field, Collection<T> input) {
        return new CollectionValidator<>(field, input);
    }

    /**
     * Create a fluent validator for an Instant
     *
     * <p>
     * Usage:
     * </p>
     *
     * <pre>
     * <code>
     * Validation.field("date", date)
     *   .inPast()
     *   .after(otherDate);
     * </code>
     * </pre>
     *
     * @param field name of the field to check (will be displayed in exception message)
     * @param input value to check
     * @return An {@link InstantValidator} for this field and value
     */
    public static InstantValidator field(String field, Instant input) {
        return new InstantValidator(field, input);
    }

    /**
     * Validator dedicated to {@link String} validations
     */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class StringValidator {

        private final String field;
        private final String value;

        /**
         * Ensure that the value is not null
         *
         * @return The current validator
         * @throws MissingMandatoryValueException if the value is null
         */
        public StringValidator notNull() {
            Validation.notNull(field, value);

            return this;
        }

        /**
         * Ensure that the value is not blank (null, empty or only whitespace)
         *
         * @return The current validator
         * @throws MissingMandatoryValueException if the value is blank
         */
        public StringValidator notBlank() {
            notNull();

            if (value.isBlank()) {
                throw MissingMandatoryValueException.forBlankValue(field);
            }

            return this;
        }

        /**
         * Ensure that the input value is at least of the given length
         *
         * @param length inclusive min length of the {@link String}
         * @return The current validator
         * @throws MissingMandatoryValueException if the expected length is strictly positive
         *                                        and the value is null
         * @throws StringTooShortException        if the value is shorter than min length
         */
        public StringValidator minLength(int length) {
            if (length <= 0 && value == null) {
                return this;
            }

            notNull();

            if (value.length() < length) {
                throw StringTooShortException
                    .builder()
                    .field(field)
                    .value(value)
                    .minLength(length)
                    .build();
            }

            return this;
        }

        /**
         * Ensure that the given input value is not over the given length
         *
         * @param length inclusive max length of the {@link String}
         * @return The current validator
         * @throws StringTooLongException if the value is longer than the max length
         */
        public StringValidator maxLength(int length) {
            if (value == null) {
                return this;
            }

            if (value.length() > length) {
                throw StringTooLongException
                    .builder()
                    .field(field)
                    .value(value)
                    .maxLength(length)
                    .build();
            }

            return this;
        }
    }

    /**
     * Validator dedicated to Integer values (int and {@link Integer})
     */
    public static final class IntegerValidator {

        private final String field;
        private final Integer value;

        private IntegerValidator(String field, Integer value) {
            this.field = field;
            this.value = value;
        }

        /**
         * Ensure that the input value is positive (0 is positive)
         *
         * @return The current validators
         * @throws MissingMandatoryValueException if the value is null
         * @throws NumberValueTooLowException     if the value is negative
         */
        public IntegerValidator positive() {
            return min(1);
        }

        /**
         * Ensure that the input value is over the given value
         *
         * @param minValue inclusive min value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooLowException     if the value is under min
         */
        public IntegerValidator min(int minValue) {
            notNull(field, value);

            if (value < minValue) {
                throw NumberValueTooLowException
                    .builder()
                    .field(field)
                    .minValue(String.valueOf(minValue))
                    .value(String.valueOf(value))
                    .build();
            }

            return this;
        }

        /**
         * Ensure that the input value is under the given value
         *
         * @param maxValue inclusive max value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooHighException    if the value is over max
         */
        public IntegerValidator max(int maxValue) {
            notNull(field, value);

            if (value > maxValue) {
                throw NumberValueTooHighException
                    .builder()
                    .field(field)
                    .maxValue(String.valueOf(maxValue))
                    .value(String.valueOf(value))
                    .build();
            }

            return this;
        }
    }

    /**
     * Validator dedicated to long values (long and {@link Long})
     */
    public static final class LongValidator {

        private final String field;
        private final Long value;

        private LongValidator(String field, Long value) {
            this.field = field;
            this.value = value;
        }

        /**
         * Ensure that the input value is positive (0 is positive)
         *
         * @return The current validators
         * @throws MissingMandatoryValueException if the value is null
         * @throws NumberValueTooLowException     if the value is negative
         */
        public LongValidator positive() {
            return min(0);
        }

        /**
         * Ensure that the input value is over the given value
         *
         * @param minValue inclusive min value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooLowException     if the value is under min
         */
        public LongValidator min(long minValue) {
            notNull(field, value);

            if (value < minValue) {
                throw NumberValueTooLowException
                    .builder()
                    .field(field)
                    .minValue(String.valueOf(minValue))
                    .value(String.valueOf(value))
                    .build();
            }

            return this;
        }

        /**
         * Ensure that the input value is under the given value
         *
         * @param maxValue inclusive max value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooHighException    if the value is over max
         */
        public LongValidator max(long maxValue) {
            notNull(field, value);

            if (value > maxValue) {
                throw NumberValueTooHighException
                    .builder()
                    .field(field)
                    .maxValue(String.valueOf(maxValue))
                    .value(String.valueOf(value))
                    .build();
            }

            return this;
        }
    }

    /**
     * Validator dedicated to float values (float and {@link Float})
     */
    public static final class FloatValidator {

        private final String field;
        private final Float value;

        private FloatValidator(String field, Float value) {
            this.field = field;
            this.value = value;
        }

        /**
         * Ensure that the input value is positive (0 is positive)
         *
         * @return The current validators
         * @throws MissingMandatoryValueException if the value is null
         * @throws NumberValueTooLowException     if the value is negative
         */
        public FloatValidator positive() {
            return min(0);
        }

        /**
         * Ensure that the input value is strictly positive (0 is not strictly positive)
         *
         * @return The current validators
         * @throws MissingMandatoryValueException if the value is null
         * @throws NumberValueTooLowException     if the value is negative
         */
        public FloatValidator strictlyPositive() {
            return over(0);
        }

        /**
         * Ensure that the input value is over the given value
         *
         * @param minValue inclusive min value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooLowException     if the value is under min
         */
        public FloatValidator min(float minValue) {
            notNull(field, value);

            if (value < minValue) {
                throw tooLow(minValue);
            }

            return this;
        }

        /**
         * Ensure that the input value is over the given floor
         *
         * @param floor exclusive floor value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooHighException    if the value is under floor
         */
        public FloatValidator over(float floor) {
            notNull(field, value);

            if (value <= floor) {
                throw tooLow(floor);
            }

            return this;
        }

        private NumberValueTooLowException tooLow(float floor) {
            return NumberValueTooLowException
                .builder()
                .field(field)
                .minValue(String.valueOf(floor))
                .value(String.valueOf(value))
                .build();
        }

        /**
         * Ensure that the input value is under the given value
         *
         * @param maxValue inclusive max value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooHighException    if the value is over max
         */
        public FloatValidator max(float maxValue) {
            notNull(field, value);

            if (value > maxValue) {
                throw tooHigh(maxValue);
            }

            return this;
        }

        /**
         * Ensure that the input value is under the given ceil
         *
         * @param ceil exclusive ceil value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooHighException    if the value is over ceil
         */
        public FloatValidator under(float ceil) {
            notNull(field, value);

            if (value >= ceil) {
                throw tooHigh(ceil);
            }

            return this;
        }

        private NumberValueTooHighException tooHigh(float ceil) {
            return NumberValueTooHighException
                .builder()
                .field(field)
                .maxValue(String.valueOf(ceil))
                .value(String.valueOf(value))
                .build();
        }
    }

    /**
     * Validator dedicated to double values (double and {@link Double})
     */
    public static final class DoubleValidator {

        private final String field;
        private final Double value;

        private DoubleValidator(String field, Double value) {
            this.field = field;
            this.value = value;
        }

        /**
         * Ensure that the input value is positive (0 is positive)
         *
         * @return The current validators
         * @throws MissingMandatoryValueException if the value is null
         * @throws NumberValueTooLowException     if the value is negative
         */
        public DoubleValidator positive() {
            return min(0);
        }

        /**
         * Ensure that the input value is strictly positive (0 is not strictly positive)
         *
         * @return The current validators
         * @throws MissingMandatoryValueException if the value is null
         * @throws NumberValueTooLowException     if the value is negative
         */
        public DoubleValidator strictlyPositive() {
            return over(0);
        }

        /**
         * Ensure that the input value is over the given value
         *
         * @param minValue inclusive min value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooLowException     if the value is under min
         */
        public DoubleValidator min(double minValue) {
            notNull(field, value);

            if (value < minValue) {
                throw tooLow(minValue);
            }

            return this;
        }

        /**
         * Ensure that the input value is over the given floor
         *
         * @param floor exclusive floor value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooHighException    if the value is under floor
         */
        public DoubleValidator over(double floor) {
            notNull(field, value);

            if (value <= floor) {
                throw tooLow(floor);
            }

            return this;
        }

        private NumberValueTooLowException tooLow(double floor) {
            return NumberValueTooLowException
                .builder()
                .field(field)
                .minValue(String.valueOf(floor))
                .value(String.valueOf(value))
                .build();
        }

        /**
         * Ensure that the input value is under the given value
         *
         * @param maxValue inclusive max value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooHighException    if the value is over max
         */
        public DoubleValidator max(double maxValue) {
            notNull(field, value);

            if (value > maxValue) {
                throw tooHigh(maxValue);
            }

            return this;
        }

        /**
         * Ensure that the input value is under the given ceil
         *
         * @param ceil exclusive ceil value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooHighException    if the value is over ceil
         */
        public DoubleValidator under(double ceil) {
            notNull(field, value);

            if (value >= ceil) {
                throw tooHigh(ceil);
            }

            return this;
        }

        private NumberValueTooHighException tooHigh(double ceil) {
            return NumberValueTooHighException
                .builder()
                .field(field)
                .maxValue(String.valueOf(ceil))
                .value(String.valueOf(value))
                .build();
        }
    }

    /**
     * Validator dedicated to {@link BigDecimal} validations
     */
    public static final class BigDecimalValidator {

        private final String field;
        private final BigDecimal value;

        private BigDecimalValidator(String field, BigDecimal value) {
            this.field = field;
            this.value = value;
        }

        /**
         * Ensure that the input value is positive (0 is positive)
         *
         * @return The current validator
         * @throws MissingMandatoryValueException if the input value is null
         * @throws NumberValueTooLowException     if the input value is negative
         */
        public BigDecimalValidator positive() {
            return min(0);
        }

        /**
         * Ensure that the input value is strictly positive (0 is not strictly positive)
         *
         * @return The current validator
         * @throws MissingMandatoryValueException if the input value is null
         * @throws NumberValueTooLowException     if the input value is negative
         */
        public BigDecimalValidator strictlyPositive() {
            return over(0);
        }

        /**
         * Ensure that the input value is at least at min value
         *
         * @param minValue inclusive min value
         * @return The current validator
         * @throws MissingMandatoryValueException if the input value is null
         * @throws NumberValueTooLowException     if the input value is under the min value
         */
        public BigDecimalValidator min(long minValue) {
            return min(new BigDecimal(minValue));
        }

        /**
         * Ensure that the input value is at least at min value
         *
         * @param minValue inclusive min value
         * @return The current validator
         * @throws MissingMandatoryValueException if the input or min value is null
         * @throws NumberValueTooLowException     if the input value is under the min value
         */
        public BigDecimalValidator min(BigDecimal minValue) {
            notNull();
            Validation.notNull("minValue", minValue);

            if (value.compareTo(minValue) < 0) {
                throw tooLow(minValue);
            }

            return this;
        }

        /**
         * Ensure that the input value is over the given floor
         *
         * @param floor exclusive floor value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooLowException     if the value is under floor
         */
        public BigDecimalValidator over(long floor) {
            return over(new BigDecimal(floor));
        }

        /**
         * Ensure that the input value is over the given floor
         *
         * @param floor exclusive floor value
         * @return The current validator
         * @throws MissingMandatoryValueException if value or floor is null
         * @throws NumberValueTooLowException     if the value is under floor
         */
        public BigDecimalValidator over(BigDecimal floor) {
            notNull();
            Validation.notNull("floor", floor);

            if (value.compareTo(floor) <= 0) {
                throw tooLow(floor);
            }

            return this;
        }

        private NumberValueTooLowException tooLow(BigDecimal floor) {
            return NumberValueTooLowException
                .builder()
                .field(field)
                .minValue(String.valueOf(floor))
                .value(value.toPlainString())
                .build();
        }

        /**
         * Ensure that the input value is at most at max value
         *
         * @param maxValue inclusive max value
         * @return The current validator
         * @throws MissingMandatoryValueException if the input value is null
         * @throws NumberValueTooHighException    if the input value is over max
         */
        public BigDecimalValidator max(long maxValue) {
            return max(new BigDecimal(maxValue));
        }

        /**
         * Ensure that the input value is at most at max value
         *
         * @param maxValue inclusive max value
         * @return The current validator
         * @throws MissingMandatoryValueException if the input or max value is null
         * @throws NumberValueTooHighException    if the input value is over max
         */
        public BigDecimalValidator max(BigDecimal maxValue) {
            notNull();
            Validation.notNull("maxValue", maxValue);

            if (value.compareTo(maxValue) > 0) {
                throw tooHigh(maxValue);
            }

            return this;
        }

        /**
         * Ensure that the input value is under the given ceil
         *
         * @param ceil exclusive ceil value
         * @return The current validator
         * @throws MissingMandatoryValueException if value is null
         * @throws NumberValueTooHighException    if the value is under floor
         */
        public BigDecimalValidator under(long ceil) {
            return under(new BigDecimal(ceil));
        }

        /**
         * Ensure that the input value is under the given ceil
         *
         * @param ceil exclusive ceil value
         * @return The current validator
         * @throws MissingMandatoryValueException if value or ceil is null
         * @throws NumberValueTooHighException    if the value is under floor
         */
        public BigDecimalValidator under(BigDecimal ceil) {
            notNull();
            Validation.notNull("ceil", ceil);

            if (value.compareTo(ceil) >= 0) {
                throw tooHigh(ceil);
            }

            return this;
        }

        private NumberValueTooHighException tooHigh(BigDecimal ceil) {
            return NumberValueTooHighException
                .builder()
                .field(field)
                .maxValue(String.valueOf(ceil))
                .value(value.toPlainString())
                .build();
        }

        /**
         * Ensure that the input value is not null
         *
         * @return The current validator
         * @throws MissingMandatoryValueException if the input value is null
         */
        public BigDecimalValidator notNull() {
            Validation.notNull(field, value);

            return this;
        }
    }

    /**
     * Validator dedicated to {@link Collection} validations
     */
    public static final class CollectionValidator<T> {

        private final String field;
        private final Collection<T> value;

        private CollectionValidator(String field, Collection<T> value) {
            this.field = field;
            this.value = value;
        }

        /**
         * Ensure that the value is not null
         *
         * @return The current validator
         * @throws MissingMandatoryValueException if the value is null
         */
        public CollectionValidator<T> notNull() {
            Validation.notNull(field, value);

            return this;
        }

        /**
         * Ensure that the value is not empty (null or empty)
         *
         * @return The current validator
         * @throws MissingMandatoryValueException if the value is null or empty
         */
        public CollectionValidator<T> notEmpty() {
            notNull();

            if (value.isEmpty()) {
                throw MissingMandatoryValueException.forEmptyValue(field);
            }

            return this;
        }

        /**
         * Ensure that the size of the given input value is not over the given size
         *
         * @param maxSize inclusive max size of the {@link Collection}
         * @return The current validator
         * @throws MissingMandatoryValueException if the expected size is strictly positive
         *                                        and the value is null
         * @throws TooManyElementsException       if the size of value is over the max size
         */
        public CollectionValidator<T> maxSize(int maxSize) {
            if (maxSize <= 0 && value == null) {
                return this;
            }

            notNull();

            if (value.size() > maxSize) {
                throw TooManyElementsException
                    .builder()
                    .field(field)
                    .maxSize(maxSize)
                    .size(value.size())
                    .build();
            }

            return this;
        }

        /**
         * Ensure that no element in this {@link Collection} is null
         *
         * @return The current validator
         * @throws NullElementInCollectionException if an element is null
         */
        public CollectionValidator<T> noNullElement() {
            if (value == null) {
                return this;
            }

            if (value.stream().anyMatch(Objects::isNull)) {
                throw new NullElementInCollectionException(field);
            }

            return this;
        }
    }

    /**
     * Validator dedicated to instant value
     */
    public static final class InstantValidator {

        private static final String OTHER_FIELD_NAME = "other";

        private final String field;
        private final Instant value;

        private InstantValidator(String field, Instant value) {
            this.field = field;
            this.value = value;
        }

        /**
         * Ensure that the given instant is in the future or at current
         * Instant (considering this method invocation time)
         *
         * @return The current validator
         * @throws MissingMandatoryValueException if the input value is null
         * @throws NotAfterTimeException          if the input instant is in past
         */
        public InstantValidator inFuture() {
            return afterOrAt(Instant.now());
        }

        /**
         * Ensure that the input instant is after the given instant
         *
         * @param other exclusive after instant
         * @return The current validator
         * @throws MissingMandatoryValueException if input or other are null
         * @throws NotAfterTimeException          if the input instant is not after the other
         *                                        instant
         */
        public InstantValidator after(Instant other) {
            notNull();
            Validation.notNull(OTHER_FIELD_NAME, other);

            if (value.compareTo(other) <= 0) {
                throw NotAfterTimeException.notStrictlyAfter(field, other);
            }

            return this;
        }

        /**
         * Ensure that the input instant is after the given instant
         *
         * @param other inclusive after instant
         * @return The current validator
         * @throws MissingMandatoryValueException if input or other are null
         * @throws NotAfterTimeException          if the input instant is not after the other
         *                                        instant
         */
        public InstantValidator afterOrAt(Instant other) {
            notNull();
            Validation.notNull(OTHER_FIELD_NAME, other);

            if (value.compareTo(other) < 0) {
                throw NotAfterTimeException.notAfter(field, other);
            }

            return this;
        }

        /**
         * Ensure that the given instant is in the past or at current Instant
         * (considering this method invocation time)
         *
         * @return The current validator
         * @throws MissingMandatoryValueException if the input value is null
         * @throws NotBeforeTimeException         if the input instant is in future
         */
        public InstantValidator inPast() {
            return beforeOrAt(Instant.now());
        }

        /**
         * Ensure that the input instant is before the given instant
         *
         * @param other exclusive before instant
         * @return The current validator
         * @throws MissingMandatoryValueException if input or other are null
         * @throws NotBeforeTimeException         if the input instant is not before the other
         *                                        instant
         */
        public InstantValidator before(Instant other) {
            notNull();
            Validation.notNull(OTHER_FIELD_NAME, other);

            if (value.compareTo(other) >= 0) {
                throw NotBeforeTimeException.notStrictlyBefore(field, other);
            }

            return this;
        }

        /**
         * Ensure that the input instant is before the given instant
         *
         * @param other inclusive before instant
         * @return The current validator
         * @throws MissingMandatoryValueException if input or other are null
         * @throws NotBeforeTimeException         if the input instant is not before the other
         *                                        instant
         */
        public InstantValidator beforeOrAt(Instant other) {
            notNull();
            Validation.notNull(OTHER_FIELD_NAME, other);

            if (value.compareTo(other) > 0) {
                throw NotBeforeTimeException.notBefore(field, other);
            }

            return this;
        }

        /**
         * Ensure that the instant is not null
         *
         * @return The current validator
         * @throws MissingMandatoryValueException if the instant is null
         */
        public InstantValidator notNull() {
            Validation.notNull(field, value);

            return this;
        }
    }
}
