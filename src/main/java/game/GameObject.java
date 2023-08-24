package game;

import graphics.vec.Vec2;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

public class GameObject {

    @Setter
    private Vec2 position;

    private Vec2 rotation;
    private Vec2 scale;

    @Getter
    private UUID uuid;

    @Getter
    @Setter
    private String label;

    @Getter
    @Setter
    private GameObject parent;

    protected List<GameObject> children;

    public GameObject() {
        this.position = new Vec2(0, 0);
        this.rotation = new Vec2(0, 0);
        this.scale = new Vec2(0, 0);
        this.children = new ArrayList<>();
        this.parent = null;
        this.label = "default";
        this.uuid = UUID.randomUUID();
    }

    public GameObject getGameObject(UUID uuid) {
        GameObject returnGameObject = null;
        if (this.uuid.equals(uuid)) {
            returnGameObject = this;
        }
        for (GameObject gameObject : this.children) {
            if (gameObject.getGameObject(uuid) != null) {
                returnGameObject = gameObject.getGameObject(uuid);
            }
        }
        return returnGameObject;
    }

    public void addChild(GameObject child) {
        child.setParent(this);
        this.children.add(child);
    }

    public Vec2 getLocalPosition() {
        return new Vec2(this.position.x, this.position.y);
    }

    public Vec2 getWorldPosition() {
        Vec2 worldPosition = getWorldPositionRecursive(this, this.getLocalPosition());
        return worldPosition;
    }

    private static Vec2 getWorldPositionRecursive(GameObject gameObject, Vec2 position) {
        if (gameObject.isRoot()) {
            return gameObject.getLocalPosition();
        } else {
            Vec2 localPosition = gameObject.getLocalPosition();
            position.add(getWorldPositionRecursive(gameObject.getParent(), localPosition));
            return position;
        }
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public void update() {
        children.forEach((child) -> child.update());
    }

    public void compose() {
        children.forEach((child) -> child.compose());
    }
}
