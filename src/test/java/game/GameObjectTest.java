package game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import graphics.vec.Vec2;
import org.junit.jupiter.api.Test;

public class GameObjectTest {

    @Test
    public void shouldReturnNullParent() {
        GameObject root = new GameObject();
        assertEquals(root.getParent(), null);
    }

    @Test
    public void shouldReturnParent() {
        GameObject root = new GameObject();
        GameObject child = new GameObject();
        root.addChild(child);
        assertEquals(root, child.getParent());
    }

    @Test
    public void shouldGetNestedGameObject() {
        GameObject root = new GameObject();
        GameObject child01 = new GameObject();
        GameObject child02 = new GameObject();
        GameObject child03 = new GameObject();
        GameObject child04 = new GameObject();

        GameObject child11 = new GameObject();
        GameObject child12 = new GameObject();
        GameObject child13 = new GameObject();
        GameObject child14 = new GameObject();

        GameObject child21 = new GameObject();
        GameObject child22 = new GameObject();
        GameObject child23 = new GameObject();
        GameObject child24 = new GameObject();

        GameObject child31 = new GameObject();
        GameObject child32 = new GameObject();
        GameObject child33 = new GameObject();
        GameObject child34 = new GameObject();

        GameObject child41 = new GameObject();
        GameObject child42 = new GameObject();
        GameObject child43 = new GameObject();
        GameObject child44 = new GameObject();

        root.addChild(child01);
        root.addChild(child02);
        root.addChild(child03);
        root.addChild(child04);

        child01.addChild(child11);
        child02.addChild(child12);
        child03.addChild(child13);
        child04.addChild(child14);

        child11.addChild(child21);
        child12.addChild(child22);
        child13.addChild(child23);
        child14.addChild(child24);

        child21.addChild(child31);
        child22.addChild(child32);
        child23.addChild(child33);
        child24.addChild(child34);

        child31.addChild(child41);
        child32.addChild(child42);
        child33.addChild(child43);
        child34.addChild(child44);

        assertEquals(child44, root.getGameObject(child44.getUuid()));
        assertEquals(child32, root.getGameObject(child32.getUuid()));
        assertEquals(child11, root.getGameObject(child11.getUuid()));
        assertEquals(child24, root.getGameObject(child24.getUuid()));
    }

    @Test
    public void shouldGetWorldPosition() {
        GameObject root = new GameObject();
        root.setPosition(new Vec2(2.5, 2.5));

        GameObject child1 = new GameObject();
        child1.setPosition(new Vec2(2.5, 2.5));

        GameObject child2 = new GameObject();
        child2.setPosition(new Vec2(2.5, 2.5));

        root.addChild(child1);
        child1.addChild(child2);

        assertEquals(7.5, child2.getWorldPosition().x);
    }

    @Test
    public void shouldSetGetLabel() {
        GameObject gameObject = new GameObject();
        gameObject.setLabel("fuzzy");
        assertEquals("fuzzy", gameObject.getLabel());
    }
}
