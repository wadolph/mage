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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author TheElk801
 */
public class HiddenHerd extends CardImpl {

    public HiddenHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // When an opponent plays a nonbasic land, if Hidden Herd is an enchantment, Hidden Herd becomes a 3/3 Beast creature.
        this.addAbility(new ConditionalTriggeredAbility(
                new HiddenHerdAbility(),
                new SourceMatchesFilterCondition(StaticFilters.FILTER_ENCHANTMENT_PERMANENT),
                "When an opponent plays a nonbasic land, if {this} is an enchantment, {this} becomes a 3/3 Beast creature."
        ));
    }

    public HiddenHerd(final HiddenHerd card) {
        super(card);
    }

    @Override
    public HiddenHerd copy() {
        return new HiddenHerd(this);
    }
}

class HiddenHerdAbility extends TriggeredAbilityImpl {

    public HiddenHerdAbility() {
        super(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new HiddenHerdBeast(), "", Duration.WhileOnBattlefield, true, false), false);
    }

    public HiddenHerdAbility(final HiddenHerdAbility ability) {
        super(ability);
    }

    @Override
    public HiddenHerdAbility copy() {
        return new HiddenHerdAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return game.getOpponents(controllerId).contains(event.getPlayerId()) && !land.getSuperType().contains(SuperType.BASIC);
    }

    @Override
    public String getRule() {
        return "When an opponent plays a nonbasic land, if {this} is an enchantment, {this} becomes a 3/3 Beast creature.";
    }
}

class HiddenHerdBeast extends Token {

    public HiddenHerdBeast() {
        super("Beast", "3/3 Beast creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BEAST);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }
}
