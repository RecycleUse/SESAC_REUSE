package com.sesac.reuse.entity.itemSearch;


public enum CategoryId {

    A("A", "가전제품"),
    B("B", "고철"),

    C("C", "금속캔"),
    D("D", "대형폐기물"),
    E("E", "불연성종량제"),
    F("F", "비닐"),
    G("G", "유리병"),
    H("H", "음식물"),
    I("I", "의류"),
    J("J", "재질별분리"),
    K("K", "전문시설"),
    L("L", "전용함"),
    M("M", "종량제봉투"),
    N("N", "종이"),
    O("O", "종이팩"),
    P("P", "주의"),
    Q("Q", "플라스틱");

    private String category_id;
    private String name;

    CategoryId(String category_id, String name) {
        this.category_id = category_id;
        this.name = name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getName() {
        return name;
    }
}
