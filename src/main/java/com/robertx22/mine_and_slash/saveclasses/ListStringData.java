package com.robertx22.mine_and_slash.saveclasses;

import com.google.common.base.Splitter;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class ListStringData {

    @Store
    private List<Part> list = new ArrayList<>();

    static int MAX = 10000;

    public List<String> getList() {
        return list.stream().map(x -> x.getString()).collect(Collectors.toList());
    }

    @Storable
    public static class Part {

        Part(String string) {
            if (string.length() > MAX) {
                this.s = Splitter.fixedLength(MAX).splitToList(string);
            } else {
                this.s.add(string);
            }
        }

        public Part() {

        }

        public String getString() {
            String string = "";
            for (String x : s) {
                string += x;
            }
            return string;

        }

        @Store
        public List<String> s = new ArrayList<>();
    }

    public ListStringData() {
    }

    public ListStringData(List<String> list) {
        this.list = list.stream().map(x -> new Part(x)).collect(Collectors.toList());
    }
}
