package dev.dubhe.brace.utils.chat;


import java.util.List;

public interface MutableComponent extends Component {
    MutableComponent append(Component sibling);

    default MutableComponent append(String string) {
        return this.append(new TextComponent(string));
    }

    List<Component> getComponents();
}
