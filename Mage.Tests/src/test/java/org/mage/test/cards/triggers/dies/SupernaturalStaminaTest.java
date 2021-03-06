/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SupernaturalStaminaTest extends CardTestPlayerBase {

    /**
     * Channeler Initiate did not get it's -1/-1 counters when reanimated with
     * Supernatural Stamina. This was a sealed game against a human player.
     */
    @Test
    public void testChanneler() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // When Channeler Initiate enters the battlefield, put three -1/-1 counters on target creature you control.
        // {T}, Remove a -1/-1 counter from Channeler Initiate: Add one mana of any color to your mana pool.
        addCard(Zone.HAND, playerA, "Channeler Initiate"); // Creature 3/4 {1}{G}

        // Until end of turn, target creature gets +2/+0 and gains "When this creature dies, return it to the battlefield tapped under its owner's control."
        addCard(Zone.HAND, playerA, "Supernatural Stamina"); // Instant {B}
        // Shock deals 2 damage to target creature or player.
        addCard(Zone.HAND, playerA, "Shock"); // Instant {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Channeler Initiate");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Supernatural Stamina", "Channeler Initiate");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Shock", "Channeler Initiate");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Supernatural Stamina", 1);
        assertGraveyardCount(playerA, "Shock", 1);

        assertPermanentCount(playerA, "Channeler Initiate", 1);
        assertPowerToughness(playerA, "Channeler Initiate", 0, 1);

    }
}
