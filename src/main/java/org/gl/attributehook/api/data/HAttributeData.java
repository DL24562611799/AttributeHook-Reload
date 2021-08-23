package org.gl.attributehook.api.data;

import com.google.common.collect.ImmutableMap;
import org.gl.attributehook.utils.number.NumberTransform;
import org.jetbrains.annotations.NotNull;
import org.gl.attributehook.AttributeHook;
import org.gl.attributehook.utils.Strings;
import org.gl.attributehook.utils.number.NumberDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class HAttributeData {

     private final Map<String, double[]> values = new ConcurrentHashMap<>();

    /**
     * 根据字符串集合操作属性值
     * @param type 操作类型
     * @param lore 字符串集合
     * @return 自己
     */
    public synchronized HAttributeData operation(@NotNull Operation type, @NotNull List<String> lore) {
        lore.stream().filter(Strings::nonEmpty).forEach(s -> this.operation(type, Tool.toAttributeLore(Strings.clearColorCode(s)), Tool.toAttributeValues(s)));
        return this;
    }

    /**
     * 根据某个属性值操作当前属性值
     * @param type 操作类型
     * @param data 属性值
     * @return 自己
     */
    public synchronized HAttributeData operation(@NotNull Operation type, @NotNull HAttributeData data) {
        for (Map.Entry<String, double[]> entry : data.getValues().entrySet()) {
            this.operation(type, entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * 根据属性描述和具体值操作当前属性值
     * @param type 操作类型
     * @param attributeLore 属性描述
     * @param values 具体属性值
     * @return 自己
     */
    public synchronized HAttributeData operation(@NotNull Operation type, @NotNull String attributeLore, double[] values) {
        double[] vs = this.getValue(attributeLore);
        if (vs != null && vs.length >= values.length) {
            switch (type) {
                case ADD:
                    for (int i = 0; i < vs.length; i++) {
                        vs[i] = NumberDecimal.add(vs[i], values[i]).doubleValue();
                    }
                    break;
                case TAKE:
                    for (int i = 0; i < vs.length; i++) {
                        vs[i] = Math.max(NumberDecimal.subtract(vs[i], values[i]).doubleValue(), 0);
                    }
                    break;
                case SET:
                    for (int i = 0; i < vs.length; i++) {
                        vs[i] = Math.max(values[i], 0);
                    }
                    break;
            }
            this.values.put(attributeLore, vs);
        }
        return this;
    }

    /**
     * 根据属性描述获取对应的具体属性值
     * @param attributeLore 属性描述
     * @return 具体值
     */
    public double[] getValue(@NotNull String attributeLore) {
        return this.values.getOrDefault(attributeLore, new double[]{0, 0});
    }

    /**
     * 将当前的属性值转换为属性描述集合
     * @return 属性集合
     */
    public List<String> getAttributes() {
        List<String> lore = new ArrayList<>();
        for (Map.Entry<String, double[]> entry : this.values.entrySet()) {
            StringBuilder builder = new StringBuilder(entry.getKey()).append(": ");
            double[] values = entry.getValue();
            double max = NumberTransform.max(values);
            double min = NumberTransform.min(values);
            if (min > 0) {
                builder.append(min);
                if (max > 0 && max > min) {
                    builder.append(" - ").append(max);
                }
            }else if (max > 0){
                builder.append(max);
            }
            lore.add(builder.toString());
        }
        return lore;
    }

    public ImmutableMap<String, double[]> getValues() {
        return ImmutableMap.copyOf(this.values);
    }


    public static final class Tool {
        public static String toAttributeLore(@NotNull String lind) {
            return lind.replaceAll("[0-9]", "").replaceAll("\\+|-|\\*|/|&|=|(>=)|(<=)", "").replace(" ", "").replace("\\.", "").replace(":", "");
        }

        public static double[] toAttributeValues(@NotNull String lind) {
            double[] arrays = new double[2];
            List<String> placeholder = AttributeHook.getPlugin().getConfig().getStringList("placeholder");
            if (placeholder != null && placeholder.size() > 0) {
                for (String s : placeholder) {
                    if (lind.contains(s)) {
                        String[] split = lind.split(s);
                        arrays[0] = NumberTransform.extractNumber(split[0]).doubleValue();
                        arrays[1] = NumberTransform.extractNumber(split[1]).doubleValue();
                        return arrays;
                    }
                }
            }
            if (lind.contains("-")) {
                String[] split = lind.split("-");
                arrays[0] = NumberTransform.extractNumber(split[0]).doubleValue();
                arrays[1] = NumberTransform.extractNumber(split[1]).doubleValue();
            }else {
                arrays[0] = NumberTransform.extractNumber(lind).doubleValue();
            }
            return arrays;
        }
    }
}
