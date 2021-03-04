package edu.ufl.cise.cs1.controllers;

import game.controllers.AttackerController;
import game.models.Actor;
import game.models.Defender;
import game.models.Game;
import game.models.Node;

import java.util.ArrayList;
import java.util.List;

public final class StudentAttackerController implements AttackerController
{
	public void init(Game game) { }

	public void shutdown(Game game) { }

	public int update(Game game,long timeDue)
	{
		List<Node> pills = game.getPillList();
		int action = 4;

		Node target = game.getAttacker().getTargetNode(pills, true);
		if(target == null) {
			return action;
		}
		int nextDir = game.getAttacker().getNextDir(target, true);

		// Added Code
		List<Defender> defenders = game.getDefenders();
		Node curPosition = game.getAttacker().getLocation();

		int closestDistance = Integer.MAX_VALUE;
		List<Defender> closestDefenders = new ArrayList<>();
		for (Defender d : defenders) {
			Node defenderLoc = d.getLocation();
			int distance = curPosition.getPathDistance(defenderLoc);
			if (distance < 10 && distance > 0 && d.isVulnerable()) {
				return game.getAttacker().getNextDir(defenderLoc, true);
			}

			if (distance < 15 && distance > 0 && !d.isVulnerable()) {
				if (closestDistance > distance) {
					closestDistance = distance;
					closestDefenders.add(0, d);
				}
				else {
					closestDefenders.add(d);
				}
			}
		}

		if (closestDefenders.size() > 0) {
			Defender closest = closestDefenders.get(0);
			List<Integer> possibleDirs = game.getAttacker().getPossibleDirs(true);

			int enemyDir = closest.getDirection();
			int ourDir = game.getAttacker().getDirection();
			int ourOppDir = game.getAttacker().getReverse();
			boolean behind = game.getAttacker().getNextDir(closest.getLocation(), true) == ourOppDir;

			if (enemyDir == game.getAttacker().getDirection()) {
				if (behind) {
					if (nextDir == ourOppDir) {
						for (int x : possibleDirs) {
							boolean good = true;

							if (x == nextDir) {
								good = false;
							}
							for (Defender d : closestDefenders) {
								if (d.getReverse() == x) {
									good = false;
								}
							}
							if (good) {
								return x;
							}
						}
					}
				}
				else {
					for (int x : possibleDirs) {
						boolean good = true;
						for (Defender d : closestDefenders) {
							if (d.getReverse() == x && ourDir != d.getDirection()) {
								good = false;
							}
						}
						if (good) {
							return x;
						}
					}
				}
			}
			else {
				boolean nextDirIsGood = true;
				for (Defender d : closestDefenders) {
					int dirTowardsEnemy = game.getAttacker().getNextDir(d.getLocation(), true);
					if (dirTowardsEnemy == nextDir) {
						nextDirIsGood = false;
					}
				}

				if (nextDirIsGood) {
					for (int x : possibleDirs) {
						if (x == nextDir) {
							return x;
						}
					}
				}
			}
		}

		Node def1 = game.getDefender(1).getLocation();
		int run1  = game.getAttacker().getNextDir(def1, false);
		int find1  = game.getAttacker().getNextDir(def1, true);
		int pathD1 = def1.getPathDistance(game.getAttacker().getLocation());

		Node def2 = game.getDefender(2).getLocation();
		int run2  = game.getAttacker().getNextDir(def2, false);
		int find2  = game.getAttacker().getNextDir(def2, true);
		int pathD2 = def2.getPathDistance(game.getAttacker().getLocation());

		Node def3 = game.getDefender(3).getLocation();
		int run3  = game.getAttacker().getNextDir(def3, false);
		int find3  = game.getAttacker().getNextDir(def3, true);
		int pathD3 = def3.getPathDistance(game.getAttacker().getLocation());

		Node def4 = game.getDefender(0).getLocation();
		int run4  = game.getAttacker().getNextDir(def4, false);
		int find4  = game.getAttacker().getNextDir(def4, true);
		int pathD4 = def4.getPathDistance(game.getAttacker().getLocation());

		if(pathD1 < 11)
		{
			if(game.getDefender(1).isVulnerable())
			{
				if(pathD1 < 5)
				{
					if(find1 > -1) {
						return find1;
					}
				}
			}
			else if(game.getAttacker().getDirection() == game.getDefender(1).getDirection())
			{
				return nextDir;
			}
			else
			{
				if(run1 > -1) {
					return run1;
				}
			}
		}
		else if(pathD2 < 11)
		{
			if(game.getDefender(2).isVulnerable())
			{
				if(pathD2 < 5)
				{
					if(find2 > -1) {
						return find2;
					}
				}
			}
			else if(game.getAttacker().getDirection() == game.getDefender(2).getDirection())
			{
				return nextDir;
			}
			else
			{
				if(run2 > -1) {
					return run2;
				}
			}
		}
		else if(pathD3 < 11)
		{
			if(game.getDefender(3).isVulnerable())
			{
				if(pathD3 < 5)
				{
					if(find3 > -1) {
						return find3;
					}
				}
			}
			else if(game.getAttacker().getDirection() == game.getDefender(3).getDirection())
			{
				return nextDir;
			}
			else
			{
				if(run3 > -1) {
					return run3;
				}
			}
		}
		else if(pathD4 < 11)
		{
			if(game.getDefender(0).isVulnerable())
			{
				if(pathD4 < 5)
				{
					if(find4 > -1) {
						return find4;
					}
				}
			}
			else if(game.getAttacker().getDirection() == game.getDefender(0).getDirection())
			{
				return nextDir;
			}
			else
			{
				if(run4 > -1) {
					return run4;
				}
			}
		}
		return nextDir;
	}
}