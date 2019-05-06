package com.github.andyshao.util.stream;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
    public static final Set<Collector.Characteristics> CH_CONCURRENT_ID =
        Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT , Collector.Characteristics.UNORDERED , Collector.Characteristics.IDENTITY_FINISH));
    public static final Set<Collector.Characteristics> CH_CONCURRENT_NOID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.CONCURRENT , Collector.Characteristics.UNORDERED));
    public static final Set<Collector.Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    public static final Set<Collector.Characteristics> CH_UNORDERED_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED , Collector.Characteristics.IDENTITY_FINISH));
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
    
    public static final <TT, AA, RR> CollectorImpl.Builder<TT, AA, RR> builder() {
    	return new CollectorImpl.Builder<TT, AA, RR>();
    }
    
    public static final <TT, AA> CollectorImpl.Builder<TT, AA, AA> biBuilder() {
    	return CollectorImpl.<TT, AA, AA>builder()
    		.withFinisher(it -> it)
    		.withCharacteristics(CH_ID);
    }

    public static class Builder<TT, AA, RR> {
        private Supplier<AA> supplier;
        private BiConsumer<AA , TT> accumulator;
        private BinaryOperator<AA> combiner;
        private Function<AA , RR> finisher;
        private Set<Characteristics> characteristics;
        
        public Builder<TT, AA, RR> withCharacteristics(Set<Characteristics> characteristics){
            this.characteristics = characteristics;
            return this;
        }
        
        public Builder<TT, AA, RR> withFinisher(Function<AA, RR> finisher){
            this.finisher = finisher;
            return this;
        }
        
        public Builder<TT, AA, RR> withCombiner(BinaryOperator<AA> combiner){
            this.combiner = combiner;
            return this;
        }
        
        public Builder<TT, AA, RR> withAccumulator(BiConsumer<AA , TT> accumulator){
            this.accumulator = accumulator;
            return this;
        }
        
        public CollectorImpl<TT, AA, RR> build() {
            return new CollectorImpl<>(supplier , accumulator , combiner , finisher , characteristics);
        }
        
        public Builder<TT, AA, RR> withSupplier(Supplier<AA> supplier){
            this.supplier = supplier;
            return this;
        }
    }
}
