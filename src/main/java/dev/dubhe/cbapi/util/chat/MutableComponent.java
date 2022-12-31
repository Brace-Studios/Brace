package dev.dubhe.cbapi.util.chat;


public interface MutableComponent extends Component {
    MutableComponent append(Component sibling);

    default MutableComponent append(String string) {
        return this.append(new TextComponent(string));
    }
}
