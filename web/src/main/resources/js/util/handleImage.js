export function setSrcUserpic(profile) {
    if (profile.userpic.startsWith("https://"))
        return profile.userpic;
    return `/api/profile/${profile.id}/avatar`;
}
