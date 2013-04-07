package game.zelda.usables;

import java.awt.Graphics2D;
import java.util.Iterator;

import engine.Game;
import engine.entity.enemy.AbstractEnemy;
import engine.entity.usable.AbstractWeapon;
import engine.graphics.sprite.AnimatedSprite;
import engine.graphics.sprite.SpriteSheet;
import engine.graphics.sprite.SpriteUtils;
import engine.map.tiled.MetaTilesNumber;
import engine.sound.AbstractSound;
import game.zelda.player.Link;

public class MegatonHammer extends AbstractWeapon {
	
	private AnimatedSprite spriteN;
	private AnimatedSprite spriteE;
	private AnimatedSprite spriteS;
	private AnimatedSprite spriteW;

	private AnimatedSprite sprite;
	
	private AbstractSound swingSound;
	
	private AbstractSound crushSound;
	
	public MegatonHammer(Link link) {
		super(link);
		damage = 2;
		using = false;
		SpriteSheet entities = (SpriteSheet) Game.sprites.get("entities");
		
		spriteN = new AnimatedSprite(entities.range(772, 774), 50);
		spriteN.oneCycle(true);
		spriteN.collisionOffset(2);
		spriteS = new AnimatedSprite(entities.range(672, 674), 50);
		spriteS.oneCycle(true);
		spriteS.collisionOffset(2);
		spriteE = new AnimatedSprite(entities.range(722, 724), 50);
		spriteE.oneCycle(true);
		spriteE.collisionOffset(2);
		spriteW = SpriteUtils.flipHorizontal(spriteE);
		spriteW.oneCycle(true);
		spriteW.collisionOffset(2);
		swingSound = Game.sounds.get("sword_slash1");
		crushSound = Game.sounds.get("rock_shatter");
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(!using) {
			return;
		}
		sprite.draw(g, x() + game.map().offset().x(), y() + game.map().offset().y());
		// collide with rocks
		int offX = user.mapX();
		int offY = user.mapY();
		smash(offX - 1, offY - 1); 
		smash(offX, offY - 1); 
		smash(offX + 1, offY - 1);
		smash(offX - 1, offY); 
		smash(offX, offY); 
		smash(offX + 1, offY);
		smash(offX - 1, offY + 1); 
		smash(offX, offY + 1); 
		smash(offX + 1, offY + 1);
	}

	@Override
	public void handle() {
		if (using()) {
			if(sprite.doneAnimating()) {
				using = false;
				user.face(user.face());
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
		((Link)user).attackFace(user.face());

		switch (user.face()) {
			case NORTH:
				sprite = spriteN;
				locate(user.x() - 2, user.y() - 9);
				break;
			case EAST:
				sprite = spriteE;
				locate(user.x() + 12, user.y() + 2);
				break;
			case SOUTH:
				sprite = spriteS;
				locate(user.x() - 2, user.y() + 9);
				break;
			case WEST:
				sprite = spriteW;
				locate(user.x() + -12, user.y() + 2);
				break;
		}
		sprite.reset();
	}
	
	private void smash(int x, int y) {
		if(x < 0 || y < 0 || x >= game.map().width() || y >= game.map().height()) {
			return;
		}
		if(game.map().metaLayer()[x][y].value() == MetaTilesNumber.ROCK) {
			if(game.map().renderLayers()[1][x][y].rectangleCollide(sprite)) {
				game.map().renderLayers()[1][x][y].value(0);
				game.map().metaLayer()[x][y].value(0);
				game.map().collisionLayer()[x][y].value(0);
				crushSound.play();
			}
		}
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
