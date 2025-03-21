package fr.heriamc.api.utils;

public enum HeriaSkull {

    LIME("f9b43f813dc1f8c853454c45f1ede94f43873df4742cd90d18ec842fafb8878d"),
    YELLOW("3da29fe23b69f76bc49474102226b0699ca2a5ad11f7a48542b13ad9cabaf89d"),
    RED("c3a03c06ffe2356ce00aef5b708878d2fe4365a97bc4dae1e1542c27b2eb30dd"),
    ORANGE("ee30480ed2834f5b7cc81dcb1dc7e4766792430c49dad4dbcd10a93c5e1457bc"),

    GRAY_BACKWARDS("74133f6ac3be2e2499a784efadcfffeb9ace025c3646ada67f3414e5ef3394"),
    GRAY_FORWARD("e02fa3b2dcb11c6639cc9b9146bea54fbc6646d855bdde1dc6435124a11215d"),

    GREEN_PLUS("5ff31431d64587ff6ef98c0675810681f8c13bf96f51d9cb07ed7852b2ffd1"),
    RED_MINUS("4e4b8b8d2362c864e062301487d94d3272a6b570afbf80c2c5b148c954579d46"),

    BOX_BLUE("d30531e731b721d69440f1e3b268240f1fd86b2ab1c58b8a0f36f1b00e485689"),
    BOX_MAGENTA("3f015d44f17c5d824c3b73d5bcc26ac358adbda5ed47bdd05cf72bf31bf8af78"),
    BOX_RED("4c8139161791106fdfe906ccaf39da3acecda9a2f8a4502e1a4a30785ceb6a0b"),
    BOX_ORANGE("dd17f54d5d95da0f4866aaf0e5d2d85dc27e2ab22c8d0f4e53e846d10e8755ee"),
    BOX_PINK("3f015d44f17c5d824c3b73d5bcc26ac358adbda5ed47bdd05cf72bf31bf8af78"),
    BOX_GREEN("7f763b85db1714e0fa572469f5afc3ec4dc2aa569751219f736b56a0229335d8"),
    BOX_YELLOW("4e93fe2f24486e00c2c55fff6cba49f05e237d8ccc3637d0d153e2f545f7fec0"),

    ;

    private final String data;

    HeriaSkull(String data) {
        this.data = data;
    }

    public String getURL(){
        return "http://textures.minecraft.net/texture/" + data;
    }
}
