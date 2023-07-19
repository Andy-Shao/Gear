package com.github.andyshao.util.stream;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jan 16, 2018<br>
 * Encoding: UNIX UTF-8
 * 
 * @author weichuang.shao
 *
 * @param <T> the type of input elements to the reduction operation
 * @param <A> the mutable accumulation type of the reduction operation (often
 *            hidden as an implementation detail)
 * @param <R> the result type of the reduction operation
 * @since 3.2.9
 */
@AllArgsConstructor
@NoArgsConstructor
public class CollectorImpl<T , A , R> implements Collector<T , A , R> {
    private Supplier<A> supplier;
    private BiConsumer<A , T> accumulator;
    private BinaryOperator<A> combiner;
    private Function<A , R> finisher;
    private Set<Characteristics> characteristics;
    /**CH concurrent ID*/
    public static final Set<Collector.Characteristics> CH_CONCURRENT_ID =
        Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT , Collector.Characteristics.UNORDERED , Collector.Characteristics.IDENTITY_FINISH));
    /**CH concurrent NOID*/
    public static final Set<Collector.Characteristics> CH_CONCURRENT_NOID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT , Collector.Characteristics.UNORDERED));
    /**CH ID*/
    public static final Set<Collector.Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    /**CH Unordered ID*/
    public static final Set<Collector.Characteristics> CH_UNORDERED_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED , Collector.Characteristics.IDENTITY_FINISH));
    /**CH NOID*/
    public static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();

    @Override
    public Supplier<A> supplier() {
        return this.supplier;
    }

    @Override
    public BiConsumer<A , T> accumulator() {
        return this.accumulator;
    }

    @Override
    public BinaryOperator<A> combiner() {
        return this.combiner;
    }

    @Override
    public Function<A , R> finisher() {
        return this.finisher;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return this.characteristics;
    }

    /**
     * builder
     * @return {@link CollectorImpl.Builder}
     * @param <TT> input type
     * @param <AA> accumulate type
     * @param <RR> return type
     */
    public static final <TT, AA, RR> CollectorImpl.Builder<TT, AA, RR> builder() {
    	return new CollectorImpl.Builder<TT, AA, RR>();
    }

    /**
     * return type is accumulate type
     * @return {@link CollectorImpl.Builder}
     * @param <TT> input type
     * @param <AA> accumulate type
     */
    public static final <TT, AA> CollectorImpl.Builder<TT, AA, AA> idBuilder() {
    	return CollectorImpl.<TT, AA, AA>builder()
    		.withFinisher(Function.identity())
    		.withCharacteristics(CH_ID);
    }

    /**
     * return type is accumulate type, and it is concurrent mode
     * @return {@link CollectorImpl.Builder}
     * @param <TT> input type
     * @param <AA> accumulate type
     */
    public static final <TT, AA> CollectorImpl.Builder<TT, AA, AA> idCurrentBuilder() {
    	return CollectorImpl.<TT, AA, AA>builder()
        		.withFinisher(Function.identity())
        		.withCharacteristics(CH_CONCURRENT_ID);
    }

    /**
     * {@link CollectorImpl} builder
     * @param <TT> input type
     * @param <AA> accumulate type
     * @param <RR> return type
     */
    public static class Builder<TT, AA, RR> {
        private Supplier<AA> supplier;
        private BiConsumer<AA , TT> accumulator;
        private BinaryOperator<AA> combiner;
        private Function<AA , RR> finisher;
        private Set<Characteristics> characteristics;

        /**
         * set {@link java.util.stream.Collector.Characteristics}
         * @param characteristics {@link java.util.stream.Collector.Characteristics}
         * @return {@link Builder}
         */
        public Builder<TT, AA, RR> withCharacteristics(Set<Characteristics> characteristics){
            this.characteristics = characteristics;
            return this;
        }

        /**
         * set accumulator to result convertor
         * @param finisher the convertor
         * @return {@link Builder}
         */
        public Builder<TT, AA, RR> withFinisher(Function<AA, RR> finisher){
            this.finisher = finisher;
            return this;
        }

        /**
         * set accumulator combination
         * @param combiner combination function
         * @return {@link Builder}
         */
        public Builder<TT, AA, RR> withCombiner(BinaryOperator<AA> combiner){
            this.combiner = combiner;
            return this;
        }

        /**
         * set accumulator
         * @param accumulator accumulated function
         * @return {@link Builder}
         */
        public Builder<TT, AA, RR> withAccumulator(BiConsumer<AA , TT> accumulator){
            this.accumulator = accumulator;
            return this;
        }

        /**
         * build operation
         * @return {@link CollectorImpl}
         */
        public CollectorImpl<TT, AA, RR> build() {
            return new CollectorImpl<>(supplier , accumulator , combiner , finisher , characteristics);
        }

        /**
         * set accumulator creator
         * @param supplier accumulator creator
         * @return {@link Builder}
         */
        public Builder<TT, AA, RR> withSupplier(Supplier<AA> supplier){
            this.supplier = supplier;
            return this;
        }
    }
}
