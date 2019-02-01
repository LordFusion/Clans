package the_fireplace.clans.clan;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.UUID;

public interface ClaimedLandCapability {

	UUID getClan();
	void setClan(UUID faction);

	class Default implements ClaimedLandCapability {
		private UUID claimingFaction;

		public Default(){
			claimingFaction = null;
		}

		@Override
		public void setClan(UUID faction){
			claimingFaction = faction;
		}

		@Override
		public UUID getClan(){
			return claimingFaction;
		}
	}

	class Storage implements Capability.IStorage<ClaimedLandCapability> {

		@Nullable
		@Override
		public NBTBase writeNBT(Capability<ClaimedLandCapability> capability, ClaimedLandCapability instance, EnumFacing side) {
			return new NBTTagString(instance.getClan() != null ? instance.getClan().toString() : "");
		}

		@Override
		public void readNBT(Capability<ClaimedLandCapability> capability, ClaimedLandCapability instance, EnumFacing side, NBTBase nbt) {
			if(nbt instanceof NBTTagString && !((NBTTagString) nbt).getString().isEmpty())
				instance.setClan(UUID.fromString(((NBTTagString) nbt).getString()));
		}
	}
}