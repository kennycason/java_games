package game.zelda.usables;

import java.awt.Graphics2D;
import java.util.Iterator;

import engine.Game;
import engine.entity.enemy.AbstractEnemy;
import engine.entity.usable.AbstractWeapon;
import engine.sound.AbstractSound;
import engine.sprite.AnimatedSprite;
import engine.sprite.SpriteSheet;
import engine.sprite.SpriteUtils;

public class MegatonHammer extends AbstractWeapon {
	
	private AnimatedSprite spriteN;
	private AnimatedSprite spriteE;
	private AnimatedSprite spriteS;
	private AnimatedSprite spriteW;

	private AnimatedSprite sprite;
	
	private AbstractSound swingSound;
	
	private boolean using = false;

	public MegatonHammer() {
		super();
		damage = 2;
		using = false;
		SpriteSheet entities = (SpriteSheet) Game.sprites.get("entities");
		
		spriteN = new AnimatedSprite(entities.range(772, 774), 50);
		spriteN.oneCycle(true);
		spriteS = new AnimatedSprite(entities.range(672, 674), 50);
		spriteS.oneCycle(true);
		spriteE = new AnimatedSprite(entities.range(722, 724), 50);
		spriteE.oneCycle(true);
		spriteW = SpriteUtils.flipHorizontal(spriteE);
		spriteW.oneCycle(true);
		swingSound = Game.sounds.get("sword_slash1");
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(!using) {
			return;
		}
		sprite.draw(g, renderX(), renderY());
	}

	@Override
	public void handle() {
		if (using()) {
			if(sprite.doneAnimating()) {
				using = false;
				game.link().face(game.link().face());
				return;
			}
			// enemies
			Iterator<AbstractEnemy> iter = game.map().enemies().iterator();
			while (iter.hasNext()) {
				AbstractEnemy enemy = iter.next();
				if (rectangleCollide(enemy)) {
					enemy.hit(damage());
				}
			}

		}
	}

	@Override
	public void use() {
		swingSound.play();
		using = true;
		game.link().attackFace(game.link().face());

		switch (game.link().face()) {
			case NORTH:
				sprite = spriteN;
				locate(game.link().x(), game.link().y() - 9);
				break;
			case EAST:
				sprite = spriteE;
				locate(game.link().x() + 13, game.link().y());
				break;
			case SOUTH:
				sprite = spriteS;
				locate(game.link().x(), game.link().y() + 11);
				break;
			case WEST:
				sprite = spriteW;
				locate(game.link().x() + -12, game.link().y() );
				break;
		}
		sprite.reset();
	}
	
	@Override
	public boolean using() {
		return using;
	}

	@Override
	public int width() {
		return sprite.width();
	}

	@Override
	public int height() {
		return sprite.height();
	}
	
	@Override
	public void menuDraw(Graphics2D g, int x, int y) {
		spriteE.sprites().get(1).draw(g, x, y);
	}

	@Override
	public String menuDisplayName() {
		return "";
	}
	
}
