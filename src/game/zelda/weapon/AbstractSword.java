package game.zelda.weapon;

import java.awt.Graphics2D;

import engine.Game;
import engine.entity.weapon.AbstractWeapon;
import engine.sound.ISound;
import engine.sound.SoundBank;
import engine.sprite.SimpleSprite;
import engine.sprite.SpriteUtils;

public abstract class AbstractSword extends AbstractWeapon {
	
	private int phase = 0;
	
	protected SimpleSprite spriteN;
	protected SimpleSprite spriteE;
	protected SimpleSprite spriteS;
	protected SimpleSprite spriteW;
	protected SimpleSprite spriteN2;
	protected SimpleSprite spriteE2;
	protected SimpleSprite spriteS2;
	protected SimpleSprite spriteW2;
	
	protected SimpleSprite sprite;
	
	protected ISound swingSound;

	protected AbstractSword(Game game, int entityNumber, int damage) {
		super(game);
		this.damage = damage;
		spriteN = new SimpleSprite(entities.get(entityNumber));
		spriteN2 = SpriteUtils.rotate(spriteN, Math.PI / 4);
		spriteS = SpriteUtils.flipVertical(spriteN);
		spriteS2 = SpriteUtils.rotate(spriteS, Math.PI / 4);
		spriteE = SpriteUtils.rotate(spriteN, Math.PI / 2);
		spriteE2 = SpriteUtils.rotate(spriteE, Math.PI / 4);
		spriteW = SpriteUtils.flipHorizontal(spriteE);
		spriteW2 = SpriteUtils.rotate(spriteW, Math.PI / 4);	
		sprite = spriteN;
		swingSound = SoundBank.getInstance().get("sword_slash1");
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(phase > 0) {
			sprite.draw(g, renderX(), renderY());
		}
	}

	@Override
	public void handle() {
		if(phase > 0) {
			game.link().attackFace(game.link().face());
			if(phase == 1) {
				switch(game.link().face()) {
					case NORTH:
						sprite = spriteN2;
						locate(game.link().x() + 11, game.link().y() - 7);	
						break;
					case EAST:
						sprite = spriteE2;
						locate(game.link().x() + 11, game.link().y() + 9);
						break;
					case SOUTH:
						sprite = spriteS2;
						locate(game.link().x() - 5, game.link().y() + 11);
						break;
					case WEST:
						sprite = spriteW2;
						locate(game.link().x() - 11, game.link().y() - 5);
						break;
				}
				phase++;
			} else if(phase >= 2) {
				switch(game.link().face()) {
					case NORTH:
						sprite = spriteN;
						locate(game.link().x() -5, game.link().y() - 9);	
						break;
					case EAST:
						sprite = spriteE;
						locate(game.link().x() + 13, game.link().y() - 3);
						break;
					case SOUTH:
						sprite = spriteS;
						locate(game.link().x() + 6, game.link().y() + 11);
						break;
					case WEST:
						sprite = spriteW;
						locate(game.link().x() + -12, game.link().y() + 5);
						break;
				}
				phase++;
				if(phase >= 6) {
					phase = 0;
					game.link().face(game.link().face());
				}
			}
		}
	}

	@Override
	public void use() {
		phase = 1;
		swingSound.play();
	}
	
	@Override
	public boolean using() {
		return phase > 0;
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
		spriteN.draw(g, x, y);
	}
	
}
