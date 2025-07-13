package game.Capabilities;

import java.util.HashSet;
import java.util.List;

/**
 * The CapabilitySet class stores unique capabilities assigned to a game component,
 * such as movement, build ability, or visual rendering traits.
 */
public class CapabilitySet {


    //These capabilities enums are UNIQUE to each game component: SO we use a hash set
    private final HashSet<Enum<?>> capabilities = new HashSet<>();

    /**
     * Checks if the component has the specified capability.
     * @param enumCapability the capability to check
     * @return a boolean: true if present, false otherwise
     */
    public boolean containsCapability(Enum<?> enumCapability) {
        return capabilities.contains(enumCapability);
    }

    /**
     * Adds a capability if it is not already present.
     * @param enumCapability the capability to add
     */
    public void addCapability(Enum<?> enumCapability) {
        if (!containsCapability(enumCapability)) {
            capabilities.add(enumCapability);
        }
    }

    /**
     * Removes a capability if it exists.
     * @param enumCapability the capability to remove
     */
    public void removeCapability(Enum<?> enumCapability) {
        if (containsCapability(enumCapability)) {
            capabilities.remove(enumCapability);
        }
    }

    /**
     * Returns a list of all current capabilities.
     * @return list of capability enums
     */
    public List<Enum<?>> getCapabilities() {
        return List.copyOf(capabilities);
    }

}
