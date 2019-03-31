package com.irvin.markov.service.impl;

import com.irvin.markov.entity.Message;
import com.irvin.markov.service.TextUtilityService;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TextUtilityServiceImpl implements TextUtilityService {

    @Override
    public Message getMarkovString(String data, int prefixLength, int suffixLength, int outputSize) {

        Random random = new Random();

        try {

            String[] temp = data.split("(?<=[,.])|(?=[,.])|\\s+");
            String[] words = new String[temp.length];
            for (int i = 0; i < temp.length; i++) {
                words[i] = temp[i].trim();
            }

            if (words.length < 1)
                return new Message(false, "Input data is empty");
            if (outputSize < prefixLength)
                return new Message(false, "Output Size > Prefix Length");
            if (outputSize >= words.length)
                return new Message(false, "Output Size >= Word Count");

            Map<String, List<String>> mapping = new HashMap<>();

            for (int i = 0; i <= (words.length - prefixLength); i++) {

                StringBuilder key = new StringBuilder(words[i]);
                for (int x = i + 1; x < i + prefixLength; x++) {
                    key.append(' ').append(words[x]);
                }

                StringBuilder value;
                if (words.length >= (i + prefixLength + suffixLength)) {
                    value = new StringBuilder(words[i + prefixLength]);
                    for (int y = i + prefixLength + 1; y < i + prefixLength + suffixLength; y++) {
                        value.append(" ").append(words[y]);
                    }
                } else {
                    value = new StringBuilder();
                }

                if (!mapping.containsKey(key.toString())) {
                    ArrayList<String> valueList = new ArrayList<>();
                    valueList.add(value.toString());
                    mapping.put(key.toString(), valueList);
                } else {
                    mapping.get(key.toString()).add(value.toString());
                }

            }

            int index = random.nextInt(mapping.size());
            String prefix = (String) mapping.keySet().toArray()[index];
            List<String> output = new ArrayList<>(Arrays.asList(prefix.split(" ")));
            List<String> values;
            String[] newPrefixArray;
            String suffix;

            do {
                values = mapping.get(prefix);
                if (values == null || values.isEmpty()) {
                    return new Message(true, generateOutput(output));
                }

                if (values.size() == 1) {
                    output.addAll(Arrays.asList(values.get(0).split(" ")));
                } else {
                    index = random.nextInt(values.size());
                    suffix = (String) values.toArray()[index];
                    output.addAll(Arrays.asList(suffix.split(" ")));
                }

                if (output.size() >= outputSize ) {
                    return new Message(true, generateOutput(output));
                }

                newPrefixArray = Arrays.copyOfRange(output.toArray(new String[0]), output.size() - prefixLength, output.size());

                prefix = String.join(" ", newPrefixArray);

            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
            return new Message(false, "Exception encountered");
        }

    }

    private String generateOutput(List<String> output) {
        if (output.get(0).matches("^.*[^a-zA-Z0-9 ].*$"))
            output.remove(0);

        return String.join(" ", output).replaceAll("\\s+(?=\\p{Punct})", "");
    }

}
